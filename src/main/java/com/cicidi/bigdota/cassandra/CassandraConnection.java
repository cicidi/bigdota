package com.cicidi.bigdota.cassandra;

import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.EnvConfig;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;


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
