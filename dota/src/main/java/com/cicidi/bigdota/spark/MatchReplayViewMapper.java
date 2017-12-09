package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.analyze.Accumulator;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchReplayViewMapper extends Mapper<CassandraRow, MatchReplayView> implements Accumulator {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private transient SparkContext sparkContext;

    public MatchReplayViewMapper(Accumulator accumulator) {
        super();
    }

    @Override
    public MatchReplayView call(CassandraRow cassandraRow) throws Exception {
        this.accumulate("MatchReplayViewCount", 1, Accumulator.LONG);
        return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
    }

    @Override
    public SparkContext getSparkContext() {
        return this.sparkContext;
    }
}
