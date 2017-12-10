package com.cicidi.bigdota.spark;

import com.cicidi.framework.spark.analyze.Accumulatable;
import org.apache.spark.SparkContext;
import org.apache.spark.util.AccumulatorV2;
import com.cicidi.bigdota.util.Constants;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;

public class TupleComparator implements Comparator<Tuple2<String, Integer>>, Serializable, Accumulatable {
    AccumulatorV2 accumulatorV2;

    public TupleComparator(SparkContext sparkContext) {
        this.accumulatorV2 = this.createAccumulator(sparkContext, Constants.TOTAL_COMBO, Accumulatable.LONG);
    }

    public int compare(Tuple2<String, Integer> t1,
                       Tuple2<String, Integer> t2) {
        this.accumulate(1L);
        return -t1._2.compareTo(t2._2);    // sort descending
    }

    @Override
    public AccumulatorV2 getAccumulator() {
        return this.accumulatorV2;
    }
}