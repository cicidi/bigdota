package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.Constants;
import org.apache.hadoop.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

/**
 * Created by cicidi on 10/9/2017.
 */
public class SparkCassandraConnector implements Serializable {

    private static final Logger logger = Logger.getLogger(SparkCassandraConnector.class);

    public JavaRDD<MatchReplayView> read() {
        SparkConf conf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", "10.0.0.49").set("spark.driver.maxResultSize", "14g");
        SparkContext sc = new SparkContext(conf);

        JavaRDD<MatchReplayView> cassandraRowsRDD = javaFunctions(sc).cassandraTable(Constants.BIG_DOTA, "replay")
                .map(cassandraRow -> {
                    return new MatchReplayView(cassandraRow.getString("match_id"), cassandraRow.getString("data"));
                });
        logger.debug("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
        return cassandraRowsRDD;

    }


}