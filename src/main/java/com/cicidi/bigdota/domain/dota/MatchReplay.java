package com.cicidi.bigdota.domain.dota;


import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cicidi on 9/15/2017.
 */
@Table(value = "replay")
public class MatchReplay implements Serializable {

    @PrimaryKey(value = "match_id")
    private long matchId;
    @Column
    private Map data;
    @Column
    private String rawData;
    @Column(value = "current_time_stamp")
    private long currentTimeStamp;

    public MatchReplay(long matchId, Map data, String rawData, long currentTimeStamp) {
        this.matchId = matchId;
        this.data = data;
        this.rawData = rawData;
        this.currentTimeStamp = currentTimeStamp;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }
}