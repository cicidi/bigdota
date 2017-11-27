package com.cicidi.bigdota.mapper;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.spark.PipelineContext;
import com.datastax.spark.connector.japi.CassandraRow;

public class MatchReplayMapper extends Mapper<CassandraRow, MatchReplay> {

    private AbstractConverter abstractConverter;

    public MatchReplayMapper(AbstractConverter abstractConverter) {
        super(MatchReplayMapper.class.getSimpleName());
        this.abstractConverter = abstractConverter;
    }

    @Override
    public MatchReplay map(PipelineContext pipelineContext, CassandraRow cassandraRow) {
        pipelineContext.addAmount(accumulatorName, 1);
        String data = abstractConverter.extract(cassandraRow.getString("raw_data"));
        return new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
    }
}
