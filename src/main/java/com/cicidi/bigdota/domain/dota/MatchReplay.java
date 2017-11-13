package com.cicidi.bigdota.domain.dota;


import com.cicidi.bigdota.util.Constants;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by cicidi on 9/15/2017.
 */
@Table(value = Constants.REPLAY_TABLE)
public class MatchReplay implements Serializable {

    @PrimaryKey(value = "match_id")
    private String matchId;
    @Column
    private String data;
    @Column(value = "raw_data")
    private String rawData;
    @Column(value = "current_time_stamp")
    private long currentTimeStamp;

    public MatchReplay(String matchId, String data, String rawData, long currentTimeStamp) {
        this.matchId = matchId;
        this.data = data;
        this.rawData = rawData;
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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