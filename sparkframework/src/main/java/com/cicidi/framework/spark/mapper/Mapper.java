package com.cicidi.framework.spark.mapper;

import com.cicidi.framework.spark.analyze.Accumulatable;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.util.AccumulatorV2;

public abstract class Mapper<T, R> implements Function<T, R>, Accumulatable {

    protected AccumulatorV2 accumulatorV2;

    public Mapper() {
    }

    public AccumulatorV2 getAccumulator() {
        return accumulatorV2;
    }
}
