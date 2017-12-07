package com.cicidi.framework.spark.mapper;

public abstract class FlatMapper<T, R> extends Mapper<T, R> {

    public FlatMapper(String accumulatorName) {
        super(accumulatorName);
        this.accumulatorName = accumulatorName;
    }


}
