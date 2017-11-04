package com.cicidi.bigdota.service;

import com.cicidi.bigdota.cassandra.CassandraConnector;
import com.cicidi.bigdota.domain.DotaPlayer;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cicidi on 10/16/2017.
 */

public class MatchReplayManagement {

    @Autowired
    private DotaReplayApi dotaReplayApi;

    @Autowired
    private CassandraConnector cassandraConnector;

    public void loadAllMatchMultithread() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<DotaPlayer> playerList = dotaReplayApi.getAllPlayers();
        int i = 0;
        for (DotaPlayer dp : playerList) {
            List<LinkedHashMap> matchReplays = dotaReplayApi.getMatchIdByAccountId(dp.getAccount_id());
            if (i < 24) {  // skip first n players
                i++;
                continue;
            }
            for (LinkedHashMap map : matchReplays) {

                Object m_Id = map.get("match_id");
                long matchId;
                if (m_Id instanceof Long == false) {
                    matchId = new Long((Integer) m_Id);
                } else {
                    matchId = (Long) map.get("match_id");
                }
                Runnable worker = new WorkerThread(matchId, cassandraConnector, dotaReplayApi);
                executor.execute(worker);
            }
        }
    }
}