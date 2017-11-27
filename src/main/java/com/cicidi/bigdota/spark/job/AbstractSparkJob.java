package com.cicidi.bigdota.spark.job;

import com.cicidi.bigdota.spark.PipelineContext;

public abstract class AbstractSparkJob<IN, OUT> {

    protected PipelineContext pipelineContext;

    abstract OUT process(IN javaRDD, PipelineContext pipelineContext);

}
