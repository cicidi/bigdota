package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.framework.spark.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by cicidi on 10/16/2017.
 */
public class MatchThread implements Callable<MatchReplay> {

    private String matchId;

    private MatchReplayRepository matchReplayRepository;

    private DotaReplayApi dotaReplayApi;

    private AbstractConverter dotaConverter;

    private final static Logger logger = LoggerFactory.getLogger(MatchThread.class);

    public MatchThread(String matchId, DotaReplayApi dotaReplayApi, MatchReplayRepository matchReplayRepository, AbstractConverter dotaConverter) {
        this.matchId = matchId;
        this.dotaReplayApi = dotaReplayApi;
        this.matchReplayRepository = matchReplayRepository;
        this.dotaConverter = dotaConverter;
    }

    @Override
    public MatchReplay call() {
        if (!matchReplayRepository.existsById(matchId)) {
            String rawData = dotaReplayApi.getReplay(matchId);
            String str = dotaConverter.extract(rawData);
            if (str.length() > 2) {
                MatchReplay matchReplay = new MatchReplay(matchId, str, rawData, System.currentTimeMillis());
                MatchReplay saved = matchReplayRepository.save(matchReplay);
                logger.debug(Thread.currentThread().getName() + " End.");
                logger.debug("Thread :" + Thread.currentThread().getName() + " : dotaReplayApiId" + dotaReplayApi.toString());
                return saved;
            }
        }
        return null;
    }
}