package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.framework.spark.analyze.Accumulator;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchReplayMapper extends Mapper<CassandraRow, MatchReplay> implements Accumulator {

    private AbstractConverter abstractConverter;
    @Autowired
    private transient SparkContext sparkContext;

    public MatchReplayMapper(AbstractConverter abstractConverter) {
        super();
        this.abstractConverter = abstractConverter;
    }

    @Override
    public MatchReplay call(CassandraRow cassandraRow) throws Exception {
        this.accumulate("MatchReplay_COUNT", 1, Accumulator.LONG);
        String data = abstractConverter.extract(cassandraRow.getString("raw_data"));
        return new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
    }

    @Override
    public SparkContext getSparkContext() {
        return this.sparkContext;
    }
}
