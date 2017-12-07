package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;

public class MatchReplayViewMapper extends Mapper<CassandraRow, MatchReplayView> {

    public MatchReplayViewMapper() {
        super(MatchReplayViewMapper.class.getSimpleName());
    }

    @Override
    public MatchReplayView call(CassandraRow cassandraRow) throws Exception {
        return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
    }
}
