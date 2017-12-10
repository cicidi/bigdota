package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.function.FlatMapFunction;

public class FlatmapPipeline extends Pipeline {
    private FlatMapFunction flatMapFunction;

    public FlatmapPipeline(PipelineContext pipelineContext, FlatMapFunction flatMapFunction) {
        super(pipelineContext);
        this.flatMapFunction = flatMapFunction;
    }

    @Override
    public void process() {
//        pipelineContext.setJavaRDD(
        AbstractJavaRDDLike abstractJavaRDDLike = pipelineContext.getJavaRDD().flatMap(flatMapFunction);
        pipelineContext.setJavaRDD(abstractJavaRDDLike);
        pipelineContext.addResult(this.getClass().getSimpleName(), abstractJavaRDDLike);
    }
}
