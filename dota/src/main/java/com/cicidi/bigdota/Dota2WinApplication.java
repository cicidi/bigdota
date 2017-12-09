package com.cicidi.bigdota;

import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.HeroDraftJob;
import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.log4j.Logger;
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
import scala.Tuple2;

import java.io.IOException;
import java.util.List;

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
    private HeroDraftJob heroDraftJob;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${env}")
    private String env;

    @Override
    public void run(ApplicationArguments arg) throws IOException {
        logger.info("*********************************");
        logger.info("env: " + env);
        logger.info("*********************************");
        MatchReplayUtil.team0_pick_amount = Integer.parseInt(arg.getSourceArgs()[0]);
        MatchReplayUtil.team1_pick_amount = Integer.parseInt(arg.getSourceArgs()[1]);
        long start = System.currentTimeMillis();
        PipelineContext pipelineContext = heroDraftJob.create();
        List<Tuple2<String, Integer>> topN = (List) (pipelineContext.getOutPut().get(Constants.TOPN));
//        downloadMatch(matchReplayManagement)
        logger.info("topN");
        topN.forEach(tuple2 -> {
            logger.info("key: " + tuple2._1);
            logger.info("value: " + tuple2._2);
        });
        long end = System.currentTimeMillis();
        logger.info("total time :" + (end - start));
        logger.info("total success matchReplay: " + MatchReplayUtil.matchCount);
        logger.info("mode map " + MatchReplayUtil.map);
    }

    public void downloadMatch(MatchReplayManagement matchReplayManagement) {
        matchReplayManagement.loadAllMatchMultithread();

    }
}