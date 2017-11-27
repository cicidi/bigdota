package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.AccumulatorConstants;
import com.cicidi.exception.ServiceException;
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

    private AbstractConverter abstractConverter;

    public JavaRDD<MatchReplayView> read(String keyspace, String tableName) {
        LongAccumulator accumulator = sparkContext.longAccumulator(AccumulatorConstants.MATCH);
        JavaRDD<MatchReplayView> cassandraRowsRDD = javaFunctions(sparkContext).cassandraTable(keyspace, tableName)
                .map((Function<CassandraRow, MatchReplayView>) cassandraRow -> {
                    accumulator.add(1L);
                    return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
                });
        logger.debug("accumulator size :" + accumulator.value());
        return cassandraRowsRDD;

    }

    public JavaRDD<MatchReplay> readRaw(String keyspace, String tableName) {
        if (abstractConverter == null) throw new ServiceException("abstractConverter not set");
        JavaRDD<MatchReplay> cassandraRowsRDD = javaFunctions(sparkContext).cassandraTable(keyspace, tableName)
                .map(cassandraRow -> {
                    String data = abstractConverter.extract(cassandraRow.getString("raw_data"));
                    MatchReplay matchReplay = new MatchReplay(cassandraRow.getString("match_id"), data, cassandraRow.getString("raw_data"), System.currentTimeMillis());
                    return matchReplay;
                });
        return cassandraRowsRDD;
    }

    public void reloadDB(JavaRDD<MatchReplay> cassandraRowsRDD, String keyspace, String tableName) {
        javaFunctions(cassandraRowsRDD).writerBuilder(keyspace, tableName, mapToRow(MatchReplay.class)).saveToCassandra();
    }

    public void setAbstractConverter(AbstractConverter abstractConverter) {
        this.abstractConverter = abstractConverter;
    }
}