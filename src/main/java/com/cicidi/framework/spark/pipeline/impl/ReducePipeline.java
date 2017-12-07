package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function2;

public class ReducePipeline<K> extends Pipeline {

    public ReducePipeline(PipelineContext pipelineContext) {
        super(pipelineContext);
    }

    @Override
    public void process() {
        pipelineContext.setJavaRDD(
                ((JavaPairRDD<K, Integer>) pipelineContext.getJavaRDD()).reduceByKey((Function2<Integer, Integer, Integer>) (a, b) -> a + b));
    }
}
