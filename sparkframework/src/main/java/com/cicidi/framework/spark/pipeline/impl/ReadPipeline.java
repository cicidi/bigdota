package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkRepository;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;

public abstract class ReadPipeline extends Pipeline {
    protected SparkRepository sparkRepository;
    protected Mapper mapper;

    public ReadPipeline(PipelineContext pipelineContext, SparkRepository sparkRepository, Mapper mapper) {
        super(pipelineContext);
        this.sparkRepository = sparkRepository;
        this.mapper = mapper;
    }

    @Override
    public void process() {
        JavaRDD javaRDD = sparkRepository.fetchAll(this.pipelineContext.getSparkContext(), this.mapper);
        pipelineContext.setJavaRDD(javaRDD);
        pipelineContext.addResult(ReadPipeline.class.getSimpleName(), javaRDD);
        pipelineContext.getAccumulatorMap().put(mapper.getAccumulator().name().toString(), mapper.getAccumulator());
    }
}
