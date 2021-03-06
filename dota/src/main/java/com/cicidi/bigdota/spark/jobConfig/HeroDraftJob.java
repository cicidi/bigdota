package com.cicidi.bigdota.spark.jobConfig;

import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.db.SparkRepository;
import com.cicidi.framework.spark.filter.Filter;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.PipelineBuilder;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class HeroDraftJob {
    @Autowired
    SparkContext sparkContext;

    @Autowired
    @Qualifier(value = "sparkCassandraRepository_heroDraftJob")
    SparkRepository sparkCassandraRepository_heroDraftJob;

    @Autowired
    @Qualifier(value = "sparkJdbcRepository_heroDraftJob")
    SparkRepository sparkJdbcRepository_heroDraftJob;

    @Autowired
    @Qualifier(value = "sparkFileSystemRepository__heroDraftJob")
    SparkRepository sparkFileSystemRepository__heroDraftJob;

    @Autowired
    @Qualifier(value = "flatMapFunction_heroDraftJob_MatchReplayView")
    FlatMapFunction flatMapFunction_heroDraftJob_MatchReplayView;
    @Autowired
    @Qualifier(value = "comparator__heroDraftJob_count")
    Comparator comparator__heroDraftJob_count;

    @Autowired
    @Qualifier(value = "matchReplayViewMapper_heroDraftJob")
    Mapper matchReplayViewMapper_heroDraftJob;

    @Autowired
    @Qualifier(value = "matchReplayMapper_heroDraftJob")
    Mapper matchReplayMapper_heroDraftJob;

    @Autowired
    @Qualifier(value = "heroFilter")
    Function heroFilter;
    @Autowired
    @Qualifier(value = "playerFilter")
    Function playerFilter;

    public PipelineBuilder job_1() {
        PipelineContext pipelineContext = new PipelineContext(sparkContext);
        return new PipelineBuilder<MatchReplayView>(pipelineContext)
                .readFrom(sparkCassandraRepository_heroDraftJob,
                        matchReplayViewMapper_heroDraftJob)
//                .filter(playerFilter)
                .filter(playerFilter, heroFilter)
                .flapmap(flatMapFunction_heroDraftJob_MatchReplayView)
                .mapToPair(1)
                .reduceByKey()
                .sortBy(comparator__heroDraftJob_count, 10000000)
//                .saveTo(sparkFileSystemRepository__heroDraftJob);
                .saveTo(sparkJdbcRepository_heroDraftJob);
    }

    public PipelineBuilder job_2() {
        PipelineContext pipelineContext = new PipelineContext(sparkContext);
        return new PipelineBuilder(pipelineContext)
                .readFrom(sparkCassandraRepository_heroDraftJob, matchReplayMapper_heroDraftJob)
                .saveTo(sparkCassandraRepository_heroDraftJob);
    }
}
