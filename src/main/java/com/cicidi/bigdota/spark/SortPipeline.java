package com.cicidi.bigdota.spark;

import java.util.Comparator;

public class SortPipeline extends Pipeline {
    public SortPipeline(PipelineContext pipelineContext, Comparator comparator) {
        super(pipelineContext);
    }
}
