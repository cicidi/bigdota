package com.cicidi.bigdota.spark.job;

import com.cicidi.bigdota.spark.PipelineContext;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class SortJob extends AbstractSparkJob<JavaPairRDD, List> {

    @Override
    List process(JavaPairRDD javaRDD, PipelineContext pipelineContext) {
        return javaRDD.takeOrdered(1000, MyTupleComparator.INSTANCE);
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
