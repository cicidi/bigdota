package com.cicidi.bigdota;

import com.cicidi.bigdota.configuration.AppConfig;
import com.cicidi.bigdota.configuration.CassandraConfig;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import com.cicidi.bigdota.spark.SparkJob;
import com.cicidi.bigdota.util.MatchReplayUtil;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * Created by cicidi on 8/21/2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.cicidi.bigdota.*")
@PropertySource("classpath:application.yml")
public class Dota2WinApplication {

    private static final Logger logger = Logger.getLogger(Dota2WinApplication.class);

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, CassandraConfig.class);
        SparkCassandraConnector sparkCassandraConnector = context.getBean(SparkCassandraConnector.class);
        SparkJob sparkJob = (SparkJob) context.getBean("sparkJob");
        MatchReplayManagement matchReplayManagement = context.getBean(MatchReplayManagement.class);

        long start = System.currentTimeMillis();
//        reloadDB(sparkJob, sparkCassandraConnector, matchReplayManagement);
//        downloadMatch(matchReplayManagement);
        mapReduceJob(sparkJob, sparkCassandraConnector);
        long end = System.currentTimeMillis();

        logger.info("total time :" + (end - start));
        logger.info("total success matchReplay: " + MatchReplayUtil.matchCount);
        logger.info("total combination: " + MatchReplayUtil.totalCombination);
        logger.info("mode map " + MatchReplayUtil.map);
        logger.info("failded replay" + MatchReplayUtil.failed);
    }

    public static void reloadDB(SparkJob sparkJob, SparkCassandraConnector sparkCassandraConnector, MatchReplayManagement matchReplayManagement) throws IOException {
        JavaRDD<MatchReplay> matchReplayJavaRDD = sparkCassandraConnector.readRaw();
        sparkCassandraConnector.reloadDB(matchReplayJavaRDD);
    }

    public static void downloadMatch(MatchReplayManagement matchReplayManagement) {
        matchReplayManagement.loadAllMatchMultithread();

    }

    public static void mapReduceJob(SparkJob sparkJob, SparkCassandraConnector sparkCassandraConnector) throws IOException {
        JavaRDD<MatchReplayView> matchReplayJavaRDD = sparkCassandraConnector.read();
        sparkJob.reduceJob(matchReplayJavaRDD);
    }


}