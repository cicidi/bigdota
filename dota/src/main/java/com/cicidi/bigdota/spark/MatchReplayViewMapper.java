package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.analyze.Accumulator;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;

public class MatchReplayViewMapper extends Mapper<CassandraRow, MatchReplayView> {

    public MatchReplayViewMapper() {
        super();
    }

    @Override
    public MatchReplayView call(CassandraRow cassandraRow) throws Exception {
        Accumulator.accumulate(this.pipelineContext.getSparkContext(), "MatchReplayViewCount", 1);
        return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
    }

}
