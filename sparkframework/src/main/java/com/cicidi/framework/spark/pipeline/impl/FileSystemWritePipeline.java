package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkFileSystemRepository;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class FileSystemWritePipeline extends WritePipeline {

    public FileSystemWritePipeline(PipelineContext pipelineContext, SparkFileSystemRepository sparkFileSystemRepository) {
        super(pipelineContext, sparkFileSystemRepository);
    }
}
