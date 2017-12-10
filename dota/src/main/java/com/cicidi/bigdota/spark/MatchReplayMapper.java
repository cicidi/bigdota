package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkContext;
import org.apache.spark.util.AccumulatorV2;
import org.springframework.stereotype.Component;

@Component
public class MatchReplayMapper extends Mapper<CassandraRow, MatchReplay> implements Accumulatable {

    private AbstractConverter abstractConverter;

    private AccumulatorV2 accumulatorV2;

    public MatchReplayMapper(SparkContext sparkContext, AbstractConverter abstractConverter) {
        super();
        this.abstractConverter = abstractConverter;
        this.accumulatorV2 = this.createAccumulator(sparkContext, "RELOAD_MATCH", Accumulatable.LONG);
    }

    @Override
    public MatchReplay call(CassandraRow cassandraRow) throws Exception {
        this.accumulate(1L);
        String data = abstractConverter.extract(cassandraRow.getString("raw_data"));
        return new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
    }

    @Override
    public AccumulatorV2 getAccumulator() {
        return this.accumulatorV2;
    }
}
