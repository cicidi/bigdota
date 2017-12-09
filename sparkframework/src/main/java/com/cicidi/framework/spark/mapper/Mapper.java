package com.cicidi.framework.spark.mapper;

import org.apache.spark.api.java.function.Function;

public abstract class Mapper<T, R> implements Function<T, R> {

    public Mapper() {
    }
}
