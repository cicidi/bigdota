package com.cicidi.framework.spark.analyze;

import org.apache.spark.SparkContext;
import org.apache.spark.util.AccumulatorV2;

import java.util.HashMap;
import java.util.Map;

public class AccumulatorCollect implements Accumulatable {

    private Map<String, AccumulatorV2> accumulatorV2Map;

    public AccumulatorCollect() {
    }

    public void addAccumulator(SparkContext sparkContext, String name, String type) {
        if (accumulatorV2Map == null) accumulatorV2Map = new HashMap<>();
        this.accumulatorV2Map.put(name, this.createAccumulator(sparkContext, name, type));
    }

    @Override
    public AccumulatorV2 getAccumulator() {
        return null;
    }
}
