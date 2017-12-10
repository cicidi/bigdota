package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.repo.DotaPlayerRepository;
import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.framework.spark.converter.AbstractConverter;
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
    private MatchReplayRepository matchReplayRepository;

    @Autowired
    private DotaPlayerRepository dotaPlayerRepository;

    @Autowired
    private AbstractConverter dotaConverter;

    private final static Logger logger = LoggerFactory.getLogger(MatchReplayManagement.class);


    Queue<String> matchList = new LinkedList<>();

//    @Autowired
//    private Validator matchDataValidator;

    public void loadAllMatchMultithread(int start, int end) {
        ExecutorService executor = Executors.newFixedThreadPool(20);

        //total player 1156
        List<DotaPlayer> playerList = dotaReplayApi.getAllPlayers();

        List<Future<String>> list = new ArrayList<Future<String>>();
        List<Future<List<String>>> matchFutureList = new ArrayList<Future<List<String>>>();
        int i = 0;

        for (DotaPlayer dp : playerList) {
            if (i < start) {
                i++;
                continue;
            }
            if (i > end) {
                break;
            }  // skip first n players
            i++;
            Optional<DotaPlayer> dotaPlayer = dotaPlayerRepository.findById(dp.getAccount_id());
            if (dotaPlayer != null
                    && dotaPlayer.isPresent()
                    && dotaPlayer.get().getMatchList() != null
                    && dotaPlayer.get().getMatchList().length() > 0)
                matchList.addAll(Arrays.asList(dotaPlayer.get().getMatchList().split(",")));
            else {
                Callable<List<String>> playerThread = new PlayerThread(dp, dotaReplayApi, dotaPlayerRepository);
                Future<List<String>> future = executor.submit(playerThread);
                matchFutureList.add(future);

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
        //total game 5096959
        int totalload = 0;
        while (matchList.size() > 0) {
            String matchId = matchList.poll();
//            if (i > 17) break;
            Callable<String> worker = new MatchThread(matchId, dotaReplayApi, matchReplayRepository, dotaConverter);
            Future<String> future = executor.submit(worker);
            list.add(future);
            totalload++;
            logger.debug("total  future: " + totalload);
        }

        int loaded = 0;
        int saved = 0;
        for (Future<String> fut : list) {
            try {
                loaded++;
                logger.debug("total  loaded: " + loaded + "/" + totalload);
                if (fut == null || fut.get() == null) {
                    logger.error("future is null");
                } else {
                    if (fut.get().equals("exist")) {
                    } else {
                        saved++;
                        logger.debug("total  saved: " + saved + "/" + loaded + "/" + totalload);
                        logger.debug("saved match" + ":" + fut.get());
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

    }
}