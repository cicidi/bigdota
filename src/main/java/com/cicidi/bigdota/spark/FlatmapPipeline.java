package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.mapper.FlatMapper;

public class FlatmapPipeline extends Pipeline {
    public FlatmapPipeline(PipelineContext pipelineContext, FlatMapper flatMapper) {
        super(pipelineContext);
    }
}
