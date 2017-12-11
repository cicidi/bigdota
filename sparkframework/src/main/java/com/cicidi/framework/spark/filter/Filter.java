package com.cicidi.framework.spark.filter;

import com.cicidi.framework.spark.analyze.Accumulatable;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.util.AccumulatorV2;

public abstract class Filter<T> implements Function<T, Boolean>, Accumulatable {

    private AccumulatorV2 accumulator;

    @Override
    public AccumulatorV2 getAccumulator() {
        return this.accumulator;
    }
}
