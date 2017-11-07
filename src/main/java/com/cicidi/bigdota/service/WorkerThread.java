package com.cicidi.bigdota.service;

import com.cicidi.bigdota.cassandra.CassandraConnection;
import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.domain.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cicidi on 10/16/2017.
 */
public class WorkerThread implements Runnable {

    private long matchId;

    private CassandraConnection cassandraConnection;

    private MatchReplayRepository matchReplayRepository;

    private DotaReplayApi dotaReplayApi;

    private final static Logger logger = LoggerFactory.getLogger(WorkerThread.class);

    public WorkerThread(long matchId, CassandraConnection cassandraConnection, DotaReplayApi dotaReplayApi, MatchReplayRepository matchReplayRepository) {
        this.matchId = matchId;
        this.cassandraConnection = cassandraConnection;
        this.dotaReplayApi = dotaReplayApi;
        this.matchReplayRepository = matchReplayRepository;
    }

    @Override
    public void run() {
        if (!matchReplayRepository.existsById(matchId)) {
            String data = dotaReplayApi.getReplay(matchId);
            if (data != null) {
                MatchReplay matchReplay = new MatchReplay(matchId, data, System.currentTimeMillis());
//                cassandraConnection.saveReplay(matchReplay);
                matchReplayRepository.save(matchReplay);
                logger.debug(Thread.currentThread().getName() + " End.");
                logger.debug("Thread :" + Thread.currentThread().getName() + " : dotaReplayApiId" + dotaReplayApi.toString());
            }
        }
    }
}