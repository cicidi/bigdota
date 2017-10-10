package com.bigdota.domain;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by cicidi on 9/15/2017.
 */
public class MatchReplay implements Serializable {
    private long matchId;
    private ByteBuffer data;
    private long currentTimeStamp;

    public MatchReplay(long matchId, ByteBuffer data, long currentTimeStamp) {
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

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

}