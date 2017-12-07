package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.db.SparkFileSystemRepository;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class FileSystemReadPipeline<T> extends ReadPipeline {


    public FileSystemReadPipeline(PipelineContext pipelineContext, SparkFileSystemRepository sparkFileSystemRepository, Mapper mapper) {
        super(pipelineContext, sparkFileSystemRepository, mapper);
    }

}
