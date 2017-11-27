package com.cicidi.bigdota.mapper;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.spark.PipelineContext;
import com.datastax.spark.connector.japi.CassandraRow;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class MatchReplayViewMapper extends Mapper<CassandraRow, MatchReplayView> {

    public MatchReplayViewMapper() {
        super(MatchReplayViewMapper.class.getSimpleName());
    }

    @Override
    public MatchReplayView map(PipelineContext pipelineContext, CassandraRow cassandraRow) {
        assertNotNull(accumulatorName);
        pipelineContext.addAmount(accumulatorName, 1);
        return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
    }
}
