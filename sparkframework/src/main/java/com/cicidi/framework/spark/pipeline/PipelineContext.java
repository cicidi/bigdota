package com.cicidi.framework.spark.pipeline;

import org.apache.commons.lang.Validate;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.util.AccumulatorV2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PipelineContext implements Serializable {

    private transient SparkContext sparkContext;

    private AbstractJavaRDDLike javaRDD;

    private Map outPut;

    private Map<String, AccumulatorV2> accumulatorMap;

    public PipelineContext(SparkContext sparkContext) {
        outPut = new HashMap();
        Validate.notNull(sparkContext);
        this.sparkContext = sparkContext;
        this.accumulatorMap = new HashMap<>();
    }

    public SparkContext getSparkContext() {
        return sparkContext;
    }

    public void addResult(Object key, Object value) {
        outPut.put(key, value);
    }

    public AbstractJavaRDDLike getJavaRDD() {
        return javaRDD;
    }

    public void setJavaRDD(AbstractJavaRDDLike javaRDD) {
        this.javaRDD = javaRDD;
    }

    public Map getOutPut() {
        return outPut;
    }

    public Map<String, AccumulatorV2> getAccumulatorMap() {
        return accumulatorMap;
    }

    public void setAccumulatorMap(Map<String, AccumulatorV2> accumulatorMap) {
        this.accumulatorMap = accumulatorMap;
    }
}

