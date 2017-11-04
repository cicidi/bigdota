package com.cicidi.bigdota.domain;

import java.io.Serializable;

/**
 * Created by cicidi on 9/15/2017.
 */
public class MatchReplay implements Serializable {
    private long matchId;
    private String data;
    private long currentTimeStamp;

    public MatchReplay(long matchId, String data, long currentTimeStamp) {
        this.matchId = matchId;
        this.data = data;
        this.currentTimeStamp = currentTimeStamp;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

}