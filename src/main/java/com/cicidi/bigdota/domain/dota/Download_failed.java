package com.cicidi.bigdota.domain.dota;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "download_failed")
public class Download_failed {

    @PrimaryKey(value = "match_id")
    private String matchId;
    @Column(value = "current_time_stamp")
    private long currentTimeStamp;


    public Download_failed() {
    }

    public Download_failed(String l) {
        this.matchId = l;
    }
}
