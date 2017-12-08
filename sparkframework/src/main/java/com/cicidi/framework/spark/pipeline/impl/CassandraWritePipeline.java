package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkCassandraRepository;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class CassandraWritePipeline extends WritePipeline {

    public CassandraWritePipeline(PipelineContext pipelineContext, SparkCassandraRepository sparkCassandraRepository) {
        super(pipelineContext, sparkCassandraRepository);
    }
}
