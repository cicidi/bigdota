package com.cicidi.framework.spark.analyze;

import org.apache.commons.lang.Validate;
import org.apache.spark.SparkContext;
import org.apache.spark.util.AccumulatorV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Accumulator {
    Logger logger = LoggerFactory.getLogger(Accumulator.class);
    String DOUBLE = "DOUBLE";
    String LONG = "LONG";


    default void accumulate(String accumulateName, Object addValue, String accumulatorType) {
        Validate.notNull(getSparkContext(), "sparkContext cannot be null");
        logger.info("MatchReplayViewCount addValue: {}", addValue);
        AccumulatorV2 accumulatorV2 = null;
        switch (accumulatorType) {
            case "DOUBLE":
                accumulatorV2 = getSparkContext().doubleAccumulator(accumulateName);
                accumulatorV2.add(addValue);
                break;

            case "LONG":
                accumulatorV2 = getSparkContext().longAccumulator(accumulateName);
                accumulatorV2.add(addValue);
                break;
        }

    }

    SparkContext getSparkContext();
}
