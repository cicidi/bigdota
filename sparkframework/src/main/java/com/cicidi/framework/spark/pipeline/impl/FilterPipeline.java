package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.filter.Filter;
import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;

public class FilterPipeline extends Pipeline {
    public FilterPipeline(PipelineContext pipelineContext, Filter filter) {
        super(pipelineContext);
    }

    @Override
    public void process() {

    }
}
