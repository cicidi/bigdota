package com.cicidi.bigdota.cassandra;

import com.cicidi.bigdota.domain.MatchReplay;
import com.datastax.driver.core.*;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.hadoop.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;


/**
 * Created by cicidi on 9/13/2017.
 */
public class CassandraConnector {
    private static final Logger logger = Logger.getLogger(CassandraConnector.class);
    private String node = "10.0.0.49";
    private Integer port = 9042;

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\project\\hadoop");

        SparkConf conf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", "127.0.0.1");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> cassandraRowsRDD = javaFunctions(sc).cassandraTable("big_dota", "replay")
                .map(new Function<CassandraRow, String>() {
                    @Override
                    public String call(CassandraRow cassandraRow) throws Exception {
                        return cassandraRow.toString();
                    }
                });
        System.out.println("done");
        System.out.println("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
    }


    private Cluster cluster;

    private Session session;

    public CassandraConnector connect() {
        Cluster.Builder b = Cluster.builder().addContactPoint(this.node);
        if (this.port != null) {
            b.withPort(port);
        }
        cluster = b.build();

        session = cluster.connect();
        return this;
    }


    public void close() {
        session.close();
        cluster.close();
    }

    public void saveReplay(MatchReplay matchReplay) {
        try {
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO big_dota.replay (match_id, data,current_time_stamp) values (?, ?, ?)");
            BoundStatement boundStatement = new BoundStatement(prepared);
            boundStatement.setLong("match_id", matchReplay.getMatchId());
//        boundStatement.setBytes("data", matchReplay.getData());
            boundStatement.setString("data", matchReplay.getData());
            boundStatement.setTime("current_time_stamp", matchReplay.getCurrentTimeStamp());
            session.executeAsync(boundStatement);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(long matchId) {
        try {

            PreparedStatement prepared = session.prepare(
                    "SELECT count(*) from big_dota.replay WHERE match_id = ? ");
            BoundStatement boundStatement = new BoundStatement(prepared);
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
