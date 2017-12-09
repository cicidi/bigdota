package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.framework.spark.analyze.Accumulator;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;

public class MatchReplayMapper extends Mapper<CassandraRow, MatchReplay> {

    private AbstractConverter abstractConverter;

    public MatchReplayMapper(AbstractConverter abstractConverter) {
        super();
        this.abstractConverter = abstractConverter;
    }

    @Override
    public MatchReplay call(CassandraRow cassandraRow) throws Exception {
        Accumulator.accumulate(this.pipelineContext.getSparkContext(), "MatchReplay_COUNT", 1);
        String data = abstractConverter.extract(cassandraRow.getString("raw_data"));
        return new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
    }
}
