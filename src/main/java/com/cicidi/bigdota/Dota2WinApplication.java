package com.cicidi.bigdota;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import com.cicidi.bigdota.spark.SparkJob;
import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.MatchReplayUtil;
import org.apache.log4j.Logger;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * Created by cicidi on 8/21/2017.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan("com.cicidi.bigdota.*")
@PropertySource("classpath:application.yml")
public class Dota2WinApplication implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(Dota2WinApplication.class);

    public static void main(String... args) throws Exception {
        SpringApplication.run(Dota2WinApplication.class, args);
    }

    @Autowired
    private SparkJob sparkJob;

    @Autowired
    private SparkCassandraConnector sparkCassandraConnector;

    @Autowired
    private SparkContext sparkContext;

    @Autowired
    private MatchReplayManagement matchReplayManagement;

    @Autowired
    private AbstractConverter dotaConvertor;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    private String tableName = Constants.REPLAY_TABLE;

    @Override
    public void run(ApplicationArguments arg) throws IOException {
        MatchReplayUtil.team0_pick_amount = Integer.parseInt(arg.getSourceArgs()[0]);
        MatchReplayUtil.team1_pick_amount = Integer.parseInt(arg.getSourceArgs()[1]);
        long start = System.currentTimeMillis();
        this.reloadDB(sparkCassandraConnector);
        this.mapReduceJob(sparkCassandraConnector);
        long end = System.currentTimeMillis();
//        downloadMatch(matchReplayManagement);
        logger.info("total time :" + (end - start));
        logger.info("total success matchReplay: " + MatchReplayUtil.matchCount);
        logger.info("mode map " + MatchReplayUtil.map);
    }

    public void reloadDB(SparkCassandraConnector sparkCassandraConnector) throws IOException {
        sparkCassandraConnector.setAbstractConverter(dotaConvertor);
        JavaRDD<MatchReplay> matchReplayJavaRDD = sparkCassandraConnector.readRaw(keyspace, tableName);
        sparkCassandraConnector.reloadDB(matchReplayJavaRDD, keyspace, tableName);
    }

    public void downloadMatch(MatchReplayManagement matchReplayManagement) {
        matchReplayManagement.loadAllMatchMultithread();

    }

    public void mapReduceJob(SparkCassandraConnector sparkCassandraConnector) throws IOException {
        JavaRDD<MatchReplayView> matchReplayJavaRDD = sparkCassandraConnector.read(keyspace, tableName);
        sparkJob.reduceJob(matchReplayJavaRDD);
    }


}