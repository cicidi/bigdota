package com.cicidi.framework.spark.analyze;

import org.apache.commons.lang.Validate;
import org.apache.spark.SparkContext;
import org.apache.spark.util.AccumulatorV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Accumulatable {
    Logger logger = LoggerFactory.getLogger(Accumulatable.class);
    String DOUBLE = "DOUBLE";
    String LONG = "LONG";


    default void accumulate(Object addValue) {
        Validate.notNull(this.getAccumulator(), "sparkContext cannot be null");
        logger.info("MatchReplayViewCount addValue: {}", addValue);
        this.getAccumulator().add(addValue);
    }


    default AccumulatorV2 createAccumulator(SparkContext sparkContext, String accumulateName, String type) {
        Validate.notNull(sparkContext, "sparkcontext cannot be null");
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

    AccumulatorV2 getAccumulator();
}
