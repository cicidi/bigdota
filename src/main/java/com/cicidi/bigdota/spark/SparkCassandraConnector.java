package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.Constants;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.log4j.Logger;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.util.LongAccumulator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

/**
 * Created by cicidi on 10/9/2017.
 */
public class SparkCassandraConnector implements Serializable {

    private static final Logger logger = Logger.getLogger(SparkCassandraConnector.class);

    @Autowired
    transient SparkContext sparkContext;

    @Autowired
    transient MatchReplayRepository matchReplayRepository;

    public SparkCassandraConnector(SparkContext sparkContext) {
        this.sparkContext = sparkContext;
    }

    public JavaRDD<MatchReplayView> read() {
        LongAccumulator accumulator = sparkContext.longAccumulator();
        JavaRDD<MatchReplayView> cassandraRowsRDD = javaFunctions(sparkContext).cassandraTable(Constants.BIG_DOTA, Constants.REPLAY_TABLE)
                .map((Function<CassandraRow, MatchReplayView>) cassandraRow -> {
                    accumulator.add(1L);
                    return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
                });
//        logger.debug("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
        logger.debug("accumulator size :" + accumulator.value());
        return cassandraRowsRDD;

    }

    public JavaRDD<MatchReplay> readRaw() {
        JavaRDD<MatchReplay> cassandraRowsRDD = javaFunctions(sparkContext).cassandraTable(Constants.BIG_DOTA, Constants.REPLAY_TABLE)
                .map(cassandraRow -> {
                    String data = new DotaConverter(cassandraRow.getString("raw_data")).getFilteredData();
                    MatchReplay matchReplay = new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
//                    matchReplayRepository.save(matchReplay);
                    return matchReplay;
                });
//        logger.debug("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
        return cassandraRowsRDD;
    }

    public void reloadDB(JavaRDD<MatchReplay> cassandraRowsRDD) {
        javaFunctions(cassandraRowsRDD).writerBuilder(Constants.BIG_DOTA, Constants.REPLAY_TABLE, mapToRow(MatchReplay.class)).saveToCassandra();
    }


}