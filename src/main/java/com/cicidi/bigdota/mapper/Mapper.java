package com.cicidi.bigdota.mapper;

import com.cicidi.bigdota.spark.PipelineContext;

public abstract class Mapper<IN, OUT> {
    protected String accumulatorName;

    public Mapper(String accumulatorName) {
        this.accumulatorName = accumulatorName;
    }

    public abstract OUT map(PipelineContext pipelineContext, IN in);
}
