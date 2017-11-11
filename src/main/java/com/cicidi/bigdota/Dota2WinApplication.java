package com.cicidi.bigdota;

import com.cicidi.bigdota.cassandra.CassandraConnection;
import com.cicidi.bigdota.configuration.AppConfig;
import com.cicidi.bigdota.configuration.CassandraConfig;
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
        long start = System.currentTimeMillis();
        System.setProperty("hadoop.home.dir", "D:\\project\\hadoop");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, CassandraConfig.class);
        SparkCassandraConnector sparkCassandraConnector = context.getBean(SparkCassandraConnector.class);
        MatchReplayManagement matchReplayManagement = context.getBean(MatchReplayManagement.class);
        SparkJob sparkJob = (SparkJob) context.getBean("sparkJob");
        matchReplayManagement.loadAllMatchMultithread();
        JavaRDD<MatchReplayView> matchReplayJavaRDD = sparkCassandraConnector.read();
        sparkJob.reduceJob(matchReplayJavaRDD);
        long end = System.currentTimeMillis();
        logger.debug("total time :" + (end - start));
        System.out.println("total success matchReplay: " + MatchReplayUtil.matchCount);
        System.out.println("failded replay" + MatchReplayUtil.failed);
    }


}