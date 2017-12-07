package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import com.cicidi.bigdota.util.Constants;

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
        pipelineContext.addResult(Constants.TOPN, list);
        //        int i = 0;
//        for (Object object : list) {
//            if (i++ < 10) {
////                logger.debug("top n: " + object.toString());
//                logger.debug("hero vs:" + HeroDetailMapService.convert(object.toString()));
//            }
//        }
    }
}
