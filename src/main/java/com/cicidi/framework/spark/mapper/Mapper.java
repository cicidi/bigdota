package com.cicidi.framework.spark.mapper;

import org.apache.spark.api.java.function.Function;

public abstract class Mapper<T, R> implements Function<T, R> {
    protected String accumulatorName;

    public Mapper(String accumulatorName) {
        this.accumulatorName = accumulatorName;
    }

//    public abstract R map(PipelineContext pipelineContext, T t);
}
