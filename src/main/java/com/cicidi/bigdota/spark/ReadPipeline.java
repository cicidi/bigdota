package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.mapper.Mapper;

public class ReadPipeline extends Pipeline {
    DataSource datasource;

    public ReadPipeline(PipelineContext pipelineContext, DataSource dataSource, Mapper mapper) {
        super(pipelineContext);
    }
}
