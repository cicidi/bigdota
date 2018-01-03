package com.cicidi.framework.spark.pipeline.impl;

import com.cicidi.framework.spark.pipeline.Pipeline;
import com.cicidi.framework.spark.pipeline.PipelineContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FilterPipeline extends Pipeline {

    private List<Function> filters;

    public FilterPipeline(PipelineContext pipelineContext, Function... filters) {
        super(pipelineContext);
        this.filters = Arrays.asList(filters);
    }

    @Override
    public void process() {
        AbstractJavaRDDLike abstractJavaRDDLike = pipelineContext.getJavaRDD();
        if (!CollectionUtils.isEmpty(filters)) {
            Iterator<Function> iterator = filters.iterator();
            while (iterator.hasNext()) {
                abstractJavaRDDLike = ((JavaRDD) abstractJavaRDDLike).filter(iterator.next());
            }
        }
        pipelineContext.setJavaRDD(abstractJavaRDDLike);
        pipelineContext.addResult(FlatmapPipeline.class.getSimpleName(), abstractJavaRDDLike);
    }
}
