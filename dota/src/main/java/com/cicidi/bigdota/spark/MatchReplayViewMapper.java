package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.mapper.Mapper;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cicidi.bigdota.util.Constants;

public class MatchReplayViewMapper extends Mapper<CassandraRow, MatchReplayView> implements Accumulatable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MatchReplayViewMapper(SparkContext sparkContext) {
        super();
        this.accumulatorV2 = this.createAccumulator(sparkContext, Constants.TOTAL_MATCH_COUNT, Accumulatable.LONG);
    }

    @Override
    public MatchReplayView call(CassandraRow cassandraRow) throws Exception {
        this.accumulate(1L);
        return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
    }

//    @Override
//    public AccumulatorV2 getAccumulator() {
//        return this.accumulatorV2;
//    }
}
