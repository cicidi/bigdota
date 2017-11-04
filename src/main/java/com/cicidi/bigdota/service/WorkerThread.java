package com.cicidi.bigdota.service;

import com.cicidi.bigdota.cassandra.CassandraConnector;
import com.cicidi.bigdota.domain.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cicidi on 10/16/2017.
 */
public class WorkerThread implements Runnable {

    private long matchId;

    private CassandraConnector cassandraConnector;

    private DotaReplayApi dotaReplayApi;
    private final static Logger logger = LoggerFactory.getLogger(WorkerThread.class);

    public WorkerThread(long matchId, CassandraConnector cassandraConnector, DotaReplayApi dotaReplayApi) {
        this.matchId = matchId;
        this.cassandraConnector = cassandraConnector;
        this.dotaReplayApi = dotaReplayApi;
    }

    @Override
    public void run() {
        if (!cassandraConnector.isExist(matchId)) {
            String data = dotaReplayApi.getReplay(matchId);
            if (data != null) {
                MatchReplay matchReplay = new MatchReplay(matchId, data, System.currentTimeMillis());
                cassandraConnector.saveReplay(matchReplay);
                logger.debug(Thread.currentThread().getName() + " End.");
                logger.debug("Thread :" + Thread.currentThread().getName() + " : dotaReplayApiId" + dotaReplayApi.toString());
            }
        }
    }
}