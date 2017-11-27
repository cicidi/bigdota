package com.cicidi.bigdota.spark;

import com.cicidi.exception.ServiceException;
import org.apache.spark.SparkContext;
import org.apache.spark.util.LongAccumulator;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class PipelineContext {
    private SparkContext sparkContext;
    private Map<String, String> config;

    private Map<String, LongAccumulator> accumulatorMap;

    public PipelineContext(SparkContext sparkContext) {
        config = new HashMap<>();
        assertNotNull(sparkContext);
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
}

