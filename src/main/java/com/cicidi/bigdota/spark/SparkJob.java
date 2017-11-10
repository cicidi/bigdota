package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.MatchReplayUtil;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cicidi on 10/16/2017.
 */
public class SparkJob {
    private final static Logger logger = LoggerFactory.getLogger(SparkJob.class);

    public void reduceJob(JavaRDD javaRDD) {
        JavaRDD<String> combo = javaRDD.flatMap(new Split());
        JavaPairRDD<String, Integer> pairs = combo.mapToPair(s -> new Tuple2<String, Integer>(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
        List list = counts.takeOrdered(10000, MyTupleComparator.INSTANCE);
        int i = 0;
        for (Object object : list) {
            if (i++ < 5)
                logger.debug(object.toString());
        }
        logger.debug("done");
    }

    static class MyTupleComparator implements
            Comparator<Tuple2<String, Integer>>, Serializable {
        final static MyTupleComparator INSTANCE = new MyTupleComparator();

        public int compare(Tuple2<String, Integer> t1,
                           Tuple2<String, Integer> t2) {
            return -t1._2.compareTo(t2._2);    // sort descending
            // return t1._2.compareTo(t2._2);  // sort ascending
        }
    }

}

class Split implements FlatMapFunction<MatchReplayView, String> {
    @Override
    public Iterator<String> call(MatchReplayView matchReplayView) throws Exception {
        return MatchReplayUtil.combine(matchReplayView);
    }
}
