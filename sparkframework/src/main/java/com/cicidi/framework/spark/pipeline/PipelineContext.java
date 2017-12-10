package com.cicidi.framework.spark.pipeline;

import com.cicidi.exception.ServiceException;
import org.apache.commons.lang.Validate;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.util.AccumulatorV2;
import org.apache.spark.util.LongAccumulator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PipelineContext implements Serializable {

    private transient SparkContext sparkContext;

    private AbstractJavaRDDLike javaRDD;

    private Map<String, String> config;

    private Map outPut;

    private Map<String, AccumulatorV2> accumulatorMap;

    public PipelineContext(SparkContext sparkContext) {
        config = new HashMap<>();
        outPut = new HashMap();
        Validate.notNull(sparkContext);
        this.sparkContext = sparkContext;
        this.accumulatorMap = new HashMap<>();
    }

    public void addProperty(String k, String v) {
        this.config.put(k, v);
    }

    public String getProperty(String k) {
        return this.config.get(k);
    }

    public SparkContext getSparkContext() {
        return sparkContext;
    }

    public void addAccumulatorName(String name) {
        if (accumulatorMap.keySet().contains(name)) {
            throw new ServiceException("accumulator name already exists");
        }
        LongAccumulator longAccumulator = sparkContext.longAccumulator(name);
        accumulatorMap.put(name, longAccumulator);
    }

    public void addAmount(String name, int accmount) {
        if (accumulatorMap.get(name) == null)
            this.addAccumulatorName(name);
        accumulatorMap.get(name).add(accmount);
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

    public void accumlate(String name, Object addValue, String type) {
        AccumulatorV2 accumulatorV2 = this.accumulatorMap.get(name);
        if (accumulatorV2 == null) {
            accumulatorV2 = this.createAccumulator(name, type);
        }
        accumulatorV2.add(addValue);
    }

    public AccumulatorV2 createAccumulator(String accumulateName, String type) {
        Validate.notNull(this.sparkContext, "sparkcontext cannot be null");
        AccumulatorV2 accumulatorV2 = null;
        switch (type) {
            case "DOUBLE":
                accumulatorV2 = sparkContext.doubleAccumulator(accumulateName);
                break;

            case "LONG":
                accumulatorV2 = sparkContext.longAccumulator(accumulateName);
                break;
        }
        return accumulatorV2;
    }
}

