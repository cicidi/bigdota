package com.cicidi.bigdota.spark;

public abstract class AbstractSparkJob<IN, OUT> {

    protected PipelineContext pipelineContext;

    abstract OUT process(IN javaRDD, PipelineContext pipelineContext);

}
