package com.cicidi.framework.spark.pipeline;

import java.io.Serializable;

public abstract class Pipeline implements Serializable {
    protected PipelineContext pipelineContext;

    public Pipeline(PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

    public abstract void process();
}
