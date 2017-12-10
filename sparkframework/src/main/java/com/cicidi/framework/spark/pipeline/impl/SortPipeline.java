package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;

import java.util.Comparator;
import java.util.List;

public class SortPipeline extends Pipeline {
    private Comparator comparator;
    private int topN;

    public SortPipeline(PipelineContext pipelineContext, Comparator comparator, int topN) {
        super(pipelineContext);
        this.comparator = comparator;
        this.topN = topN;
    }

    @Override
    public void process() {
        List list = pipelineContext.getJavaRDD().takeOrdered(topN, this.comparator);
        pipelineContext.addResult(SortPipeline.class.getSimpleName(), list);
    }
}
