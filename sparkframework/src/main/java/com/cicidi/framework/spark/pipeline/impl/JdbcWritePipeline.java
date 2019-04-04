package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkCassandraRepository;
import com.cicidi.framework.spark.db.SparkJDBCRepository;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class JdbcWritePipeline extends WritePipeline {

    public JdbcWritePipeline(PipelineContext pipelineContext, SparkJDBCRepository sparkJDBCRepository) {
        super(pipelineContext, sparkJDBCRepository);
    }
}
