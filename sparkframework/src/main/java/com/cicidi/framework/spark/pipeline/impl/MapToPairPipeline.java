package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class MapToPairPipeline<K, V> extends Pipeline {

    private V v;

    public MapToPairPipeline(PipelineContext pipelineContext, V v) {
        super(pipelineContext);
        this.v = v;
    }

    @Override
    public void process() {
//        pipelineContext.setJavaRDD(
        AbstractJavaRDDLike abstractJavaRDDLike = (pipelineContext.getJavaRDD())
                .mapToPair((PairFunction<K, K, V>) s -> new Tuple2<>(s, v));
        pipelineContext.setJavaRDD(abstractJavaRDDLike);
        pipelineContext.addResult(this.getClass().getSimpleName(), abstractJavaRDDLike);
    }
}
