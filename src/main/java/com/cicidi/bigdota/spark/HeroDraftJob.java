package com.cicidi.bigdota.spark;

import com.cicidi.framework.spark.db.SparkRepository;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.PipelineBuilder;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
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

    public PipelineContext create() {
        PipelineContext pipelineContext = new PipelineContext(sparkContext);
        new PipelineBuilder(pipelineContext)
                .readFrom(sparkCassandraRepository_heroDraftJob, matchReplayViewMapper_heroDraftJob)
                .flapmap(flatMapFunction_heroDraftJob_MatchReplayView)
                .mapToPair(1)
                .reduceByKey()
                .sortBy(comparator__heroDraftJob_count, 5)
                .saveTo(sparkFileSystemRepository__heroDraftJob)
                .run();
        return pipelineContext;
    }

}
