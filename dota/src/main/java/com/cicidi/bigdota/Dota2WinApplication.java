package com.cicidi.bigdota;

import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.jobConfig.HeroDraftJob;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.pipeline.PipelineBuilder;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import com.cicidi.framework.spark.pipeline.impl.SortPipeline;
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
import com.cicidi.bigdota.util.Constants;

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


    @Autowired
    MatchReplayManagement matchReplayManagement;

    @Override
    public void run(ApplicationArguments arg) throws IOException {
        logger.info("*********************************");
        logger.info("env: " + env);
        logger.info("*********************************");
        MatchReplayUtil.team0_pick_amount_query = Integer.parseInt(arg.getSourceArgs()[0]);
        MatchReplayUtil.team1_pick_amount_query = Integer.parseInt(arg.getSourceArgs()[1]);
        int load_start = Integer.parseInt(arg.getSourceArgs()[2]);
        int load_end = Integer.parseInt(arg.getSourceArgs()[3]);
        long start = System.currentTimeMillis();
        downloadMatch(load_start, load_end);
        PipelineContext pipelineContext_2 = heroDraftJob.job_2().run();
        PipelineContext pipelineContext = heroDraftJob.job_1().run();

        List<Tuple2<String, Integer>> topN = (List) (pipelineContext.getOutPut().get(SortPipeline.class.getSimpleName()));
        logger.info("topN");
        topN.forEach(tuple2 -> {
            logger.info("key: " + tuple2._1);
            logger.info("value: " + tuple2._2);
        });
        long end = System.currentTimeMillis();
        logger.info("total time :" + (end - start));
        logger.info("total success matchReplay: "
                + pipelineContext.getAccumulatorMap()
                .get(Accumulatable.convertAccumulatorName(Constants.TOTAL_MATCH_COUNT))
                .value());
        logger.info("mode map " + MatchReplayUtil.map);

        System.out.println("done");
    }

    public void downloadMatch(int start, int end) {
        matchReplayManagement.loadAllMatchMultithread(start, end);

    }
}