package com.cicidi.bigdota.cassandra;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.EnvConfig;
import com.datastax.driver.core.*;
import com.datastax.spark.connector.japi.CassandraRow;
import com.sun.jersey.api.client.Client;
import org.apache.hadoop.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;


/**
 * Created by cicidi on 9/13/2017.
 */
public class CassandraConnection {

    private static final Logger logger = Logger.getLogger(CassandraConnection.class);

    private String cassandraIps = EnvConfig.CASSANDRA_IP;

    private String port = EnvConfig.CASSANDRA_PORT;

    PreparedStatement prepared;
    BoundStatement boundStatement;
    @Autowired
    private Client client;

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\project\\hadoop");

        SparkConf conf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", "10.0.0.49,10.0.0.38,10.0.0.32");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> cassandraRowsRDD = javaFunctions(sc).cassandraTable(Constants.BIG_DOTA, "replay")
                .map(new Function<CassandraRow, String>() {
                    @Override
                    public String call(CassandraRow cassandraRow) throws Exception {
                        return cassandraRow.toString();
                    }
                });
        System.out.println("done");
        System.out.println("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
        System.out.println(cassandraRowsRDD.collect().size());
    }


    private Cluster cluster;

    private Session session;

    public CassandraConnection connect() {
        String[] node = cassandraIps.split(",");
        Cluster.Builder b = Cluster.builder().addContactPoints(node);
        b.withPort(Integer.parseInt(port));
        cluster = b.build();

        session = cluster.connect();

        prepared = session.prepare(
                "SELECT count(*) from " + Constants.BIG_DOTA + ".replay WHERE match_id = ? ");
        boundStatement = new BoundStatement(prepared);
        return this;
    }


    public void close() {
        session.close();
        cluster.close();
    }

    public void saveReplay(MatchReplay matchReplay) {
        try {
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO " + Constants.BIG_DOTA + ".replay (match_id, data,current_time_stamp) values (?, ?, ?)");
            BoundStatement boundStatement = new BoundStatement(prepared);
            boundStatement.setLong("match_id", matchReplay.getMatchId());
//        boundStatement.setBytes("data", matchReplay.getData());
//            boundStatement.setString("data", matchReplay.getData());
            boundStatement.setTime("current_time_stamp", matchReplay.getCurrentTimeStamp());
            session.executeAsync(boundStatement);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(long matchId) {
        try {
            boundStatement.setLong("match_id", matchId);
            ResultSet resultSet = session.execute(boundStatement);
            long count = -1;
            if (resultSet != null) {
                count = resultSet.one().getLong(0);
            }

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
