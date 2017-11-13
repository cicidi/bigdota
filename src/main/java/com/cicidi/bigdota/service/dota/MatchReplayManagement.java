package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.repo.DotaPlayerRepository;
import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.domain.dota.DotaPlayer;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import com.cicidi.bigdota.util.JSONUtil;
import com.cicidi.bigdota.validator.MatchDataValidator;
import com.cicidi.bigdota.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by cicidi on 10/16/2017.
 */

public class MatchReplayManagement {

    @Autowired
    private DotaReplayApi dotaReplayApi;

    @Autowired
    private MatchReplayRepository matchReplayRepository;

    @Autowired
    private DotaPlayerRepository dotaPlayerRepository;

    private final static Logger logger = LoggerFactory.getLogger(MatchReplayManagement.class);


    Queue<String> matchList = new LinkedList<>();

    @Autowired
    private Validator matchDataValidator;

    public void loadAllMatchMultithread() {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        List<DotaPlayer> playerList = dotaReplayApi.getAllPlayers();

        List<Future<MatchReplay>> list = new ArrayList<Future<MatchReplay>>();
        List<Future<List<String>>> matchFutureList = new ArrayList<Future<List<String>>>();
        int i = 0;

        for (DotaPlayer dp : playerList) {
            Optional<DotaPlayer> dotaPlayer = dotaPlayerRepository.findById(dp.getAccount_id());
            if (dotaPlayer != null && dotaPlayer.isPresent() && dotaPlayer.get().getMatchList() != null && dotaPlayer.get().getMatchList().length() > 0)
                matchList.addAll(Arrays.asList(dotaPlayer.get().getMatchList().split(",")));
            else {
                Callable<List<String>> playerThread = new PlayerThread(dp, dotaReplayApi, dotaPlayerRepository);
                Future<List<String>> future = executor.submit(playerThread);
                matchFutureList.add(future);//            if (i > 50) {  // skip first n players
            }
        }
        for (Future<List<String>> fut : matchFutureList) {
            try {
                if (null == fut || null == fut.get()) continue;
                List<String> m_list = fut.get();
                matchList.addAll(m_list);
                System.out.println("add match Id to quue" + ":" + m_list.toArray());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        while (matchList.size() > 0) {
            String matchId = matchList.poll();
            Callable<MatchReplay> worker = new MatchThread(matchId, dotaReplayApi, matchReplayRepository);
            Future<MatchReplay> future = executor.submit(worker);
            list.add(future);
            i++;
        }

        for (Future<MatchReplay> fut : list) {
            try {
                System.out.println("saved match" + ":" + fut.get().getMatchId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

    }

    public void reloadMatch() throws IOException {
        long current = System.currentTimeMillis();
        List<MatchReplay> list = matchReplayRepository.findAll();
        for (MatchReplay matchReplay : list) {
            if (!matchDataValidator.validate(matchReplay)) {
                DotaConverter dotaConverter = new DotaConverter(matchReplay.getRawData());
                Map data = dotaConverter.process();
                String converted = JSONUtil.getObjectMapper().writeValueAsString(data);
                matchReplay.setData(converted);
                matchReplay.setCurrentTimeStamp(current);
                matchReplayRepository.save(matchReplay);
            }
        }
    }
}