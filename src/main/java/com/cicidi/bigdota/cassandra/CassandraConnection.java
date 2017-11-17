package com.cicidi.bigdota.cassandra;

import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.EnvConfig;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.sun.jersey.api.client.Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by cicidi on 9/13/2017.
 */
@Deprecated
public class CassandraConnection {

    private final Logger logger = Logger.getLogger(getClass());

    private String cassandraIps = EnvConfig.CASSANDRA_IP;

    private String port = EnvConfig.CASSANDRA_PORT;

    PreparedStatement prepared;
    BoundStatement boundStatement;
    @Autowired
    private Client client;

//    public static void main(String[] args) {
//        System.setProperty("hadoop.home.dir", "D:\\project\\hadoop");
//
//        SparkConf conf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", "10.0.0.49,10.0.0.38,10.0.0.32");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> cassandraRowsRDD = javaFunctions(sc).cassandraTable(Constants.BIG_DOTA, Constants.REPLAY_TABLE)
//                .map(new Function<CassandraRow, String>() {
//                    @Override
//                    public String call(CassandraRow cassandraRow) throws Exception {
//                        return cassandraRow.toString();
//                    }
//                });
//        System.out.println("done");
//        System.out.println("Data as CassandraRows: \n" + StringUtils.join("\n", cassandraRowsRDD.collect()));
//        System.out.println(cassandraRowsRDD.collect().size());
//    }


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

}
