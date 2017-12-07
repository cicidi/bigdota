package com.cicidi.framework.spark.pipeline;

public abstract class Pipeline {
    protected PipelineContext pipelineContext;

    public Pipeline(PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

    public abstract void process();
}
