package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.cassandra.repo.MatchReplayRepository;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.JSONUtil;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.bigdota.validator.Validator;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
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
import java.util.Map;

/**
 * Created by cicidi on 10/16/2017.
 */
public class SparkJob {
    private final static Logger logger = LoggerFactory.getLogger(SparkJob.class);
    @Autowired
    MatchReplayRepository matchReplayRepository;
    @Autowired
    Validator matchDataValidator;

    public void reduceJob(JavaRDD javaRDD) throws IOException {
        JavaRDD<String> combo = javaRDD.flatMap(new Split());
        JavaPairRDD<String, Integer> pairs = combo.mapToPair(s -> new Tuple2<String, Integer>(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
        List list = counts.takeOrdered(10, MyTupleComparator.INSTANCE);
        int i = 0;
        for (Object object : list) {
            if (i++ < 5)
                logger.debug("top 10: " + object.toString());
        }
        String path = "/tmp/dota";
        FileUtils.deleteDirectory(new File(path));
        counts.saveAsTextFile(path);
        logger.debug("done");
    }


    public void reloadMatch(JavaRDD<MatchReplay> matchRawDataJavaRDD) {
        long current = System.currentTimeMillis();
        try {
//            List<MatchReplay> list = matchRawDataJavaRDD.collect();
            Iterator<MatchReplay> iterator = matchRawDataJavaRDD.toLocalIterator();
            while (iterator.hasNext()) {
                MatchReplay matchReplay = iterator.next();
                if (!matchDataValidator.validate(matchReplay)) {
                    DotaConverter dotaConverter = new DotaConverter(matchReplay.getRawData());
                    Map data = dotaConverter.process();
                    String converted = JSONUtil.getObjectMapper().writeValueAsString(data);
                    matchReplay.setData(converted);
                    matchReplay.setCurrentTimeStamp(current);
                    matchReplayRepository.save(matchReplay);
                }
            }
//            for (MatchReplay matchReplay : list) {
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
