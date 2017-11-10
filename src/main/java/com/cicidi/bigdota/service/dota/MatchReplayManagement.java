package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.CassandraConnection;
import com.cicidi.bigdota.cassandra.repo.DotaPlayerRepository;
import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.domain.dota.DotaPlayer;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by cicidi on 10/16/2017.
 */

public class MatchReplayManagement {

    @Autowired
    private DotaReplayApi dotaReplayApi;

    @Autowired
    private CassandraConnection cassandraConnection;

    @Autowired
    private MatchReplayRepository matchReplayRepository;

    @Autowired
    private DotaPlayerRepository dotaPlayerRepository;

    private final static Logger logger = LoggerFactory.getLogger(MatchReplayManagement.class);


    Queue<String> matchList = new LinkedList<>();

    public void loadAllMatchMultithread() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
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

    public void convert() {
    }
}