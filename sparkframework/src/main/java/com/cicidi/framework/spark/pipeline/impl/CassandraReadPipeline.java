package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkCassandraRepository;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class CassandraReadPipeline<T> extends ReadPipeline {

    public CassandraReadPipeline(PipelineContext pipelineContext, SparkCassandraRepository sparkCassandraRepository, Mapper mapper) {
        super(pipelineContext, sparkCassandraRepository, mapper);
    }
}
