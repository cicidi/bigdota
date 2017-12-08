package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.cassandra.repo.DotaPlayerRepository;
import com.cicidi.bigdota.domain.dota.DotaPlayer;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class PlayerThread implements Callable<List<String>> {

    private DotaPlayerRepository dotaPlayerRepository;
    private DotaPlayer dotaPlayer;
    private DotaReplayApi dotaReplayApi;

    private final static Logger logger = LoggerFactory.getLogger(MatchThread.class);

    public PlayerThread(DotaPlayer dotaPlayer, DotaReplayApi dotaReplayApi, DotaPlayerRepository dotaPlayerRepository) {
        this.dotaPlayer = dotaPlayer;
        this.dotaReplayApi = dotaReplayApi;
        this.dotaPlayerRepository = dotaPlayerRepository;
    }

    @Override
    public List<String> call() {
        List<String> matchList = new ArrayList<>();
        List<LinkedHashMap> matchReplays = dotaReplayApi.getMatchIdByAccountId(dotaPlayer.getAccount_id());
        for (LinkedHashMap map : matchReplays) {
            Object m_Id = map.get("match_id");
            String matchId;
            matchId = String.valueOf(m_Id);
            matchList.add(matchId);
        }
        if (matchList.size() > 0) {
            String str = String.join(",", matchList);
            dotaPlayer.setMatchList(str);
            DotaPlayer saved = dotaPlayerRepository.save(dotaPlayer);
            logger.debug(Thread.currentThread().getName() + " End.");
            logger.debug("Thread :" + Thread.currentThread().getName() + " : dotaReplayApiId" + dotaReplayApi.toString());
            return matchList;
        }
        return null;
    }
}
