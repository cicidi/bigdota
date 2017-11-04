package com.cicidi.bigdota.service;

import com.cicidi.bigdota.cassandra.CassandraConnector;
import com.cicidi.bigdota.configuration.AppConfig;
import com.cicidi.bigdota.domain.MatchReplayView;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import com.cicidi.bigdota.spark.SparkJob;
import com.cicidi.bigdota.util.MatchReplayUtil;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
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
@ComponentScan("com.cicidi.bigdota.*")
@PropertySource("classpath:application.yml")
public class Dota2WinApplication {

    private static final Logger logger = Logger.getLogger(Dota2WinApplication.class);

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.setProperty("hadoop.home.dir", "D:\\project\\hadoop");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CassandraConnector cassandraConnector = context.getBean(CassandraConnector.class);
        SparkCassandraConnector sparkCassandraConnector = context.getBean(SparkCassandraConnector.class);
        MatchReplayManagement matchReplayManagement = context.getBean(MatchReplayManagement.class);
        SparkJob sparkJob = (SparkJob) context.getBean("sparkJob");
        cassandraConnector.connect();
//        matchReplayManagement.loadAllMatchMultithread();
        JavaRDD<MatchReplayView> matchReplayJavaRDD = sparkCassandraConnector.read();
        sparkJob.reduceJob(matchReplayJavaRDD);
        cassandraConnector.close();
        long end = System.currentTimeMillis();
        logger.debug("total time :" + (end - start));
        System.out.println("total success matchReplay: " + MatchReplayUtil.matchCount);
        System.out.println("failded replay" + MatchReplayUtil.failed);
    }


}