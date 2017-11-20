package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import com.cicidi.utilities.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by cicidi on 10/16/2017.
 */
public class MatchThread implements Callable<MatchReplay> {

    private String matchId;

    private MatchReplayRepository matchReplayRepository;

    private DotaReplayApi dotaReplayApi;

    private final static Logger logger = LoggerFactory.getLogger(MatchThread.class);

    public MatchThread(String matchId, DotaReplayApi dotaReplayApi, MatchReplayRepository matchReplayRepository) {
        this.matchId = matchId;
        this.dotaReplayApi = dotaReplayApi;
        this.matchReplayRepository = matchReplayRepository;
    }

    @Override
    public MatchReplay call() {
        if (!matchReplayRepository.existsById(matchId)) {
            String rawData = dotaReplayApi.getReplay(matchId);
            Map map = new DotaConverter(rawData).process();
            String str = null;
            try {
                str = JSONUtil.getObjectMapper().writeValueAsString(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (map.size() > 0) {

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