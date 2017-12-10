package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkRepository;
import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.api.java.JavaPairRDD;

public abstract class WritePipeline extends Pipeline {
    protected SparkRepository sparkRepository;
    protected Class classType;

    //    public ReadPipeline(PipelineContext pipelineContext, DataSource dataSource, Mapper mapper) {
    public WritePipeline(PipelineContext pipelineContext, SparkRepository sparkRepository) {
        super(pipelineContext);
        this.sparkRepository = sparkRepository;
    }

    @Override
    public void process() {
        sparkRepository.save(pipelineContext.getSparkContext(), pipelineContext.getJavaRDD());
        pipelineContext.addResult(WritePipeline.class.getSimpleName(), pipelineContext.getJavaRDD());
    }
}
