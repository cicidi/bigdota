package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.EnvConfig;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.validation.Validator;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cicidi on 10/16/2017.
 */
public class SparkJob implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(SparkJob.class);
    //    @Autowired
//    transient MatchReplayRepository matchReplayRepository;
    @Autowired
    Validator matchDataValidator;

    public void reduceJob(JavaRDD javaRDD) throws IOException {
        JavaRDD<String> combo = javaRDD.flatMap(new Split());
        JavaPairRDD<String, Integer> pairs = combo.mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((Function2<Integer, Integer, Integer>) (a, b) -> a + b);
        List list = counts.takeOrdered(1000, MyTupleComparator.INSTANCE);
        int i = 0;
        for (Object object : list) {
            if (i++ < 1000)
                logger.debug("top n: " + object.toString());
        }
        FileUtils.deleteDirectory(new File(EnvConfig.outputPath));
        counts.saveAsTextFile(EnvConfig.outputPath);
        logger.debug("done");
    }


    static class MyTupleComparator implements
            Comparator<Tuple2<String, Integer>>, Serializable {
        final static MyTupleComparator INSTANCE = new MyTupleComparator();

        public int compare(Tuple2<String, Integer> t1,
                           Tuple2<String, Integer> t2) {
            return -t1._2.compareTo(t2._2);    // sort descending
        }
    }

}

class Split implements FlatMapFunction<MatchReplayView, String> {
    @Override
    public Iterator<String> call(MatchReplayView matchReplayView) throws Exception {
        return MatchReplayUtil.combine(matchReplayView);
    }


}
