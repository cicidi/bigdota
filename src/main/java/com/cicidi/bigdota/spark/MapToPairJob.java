package com.cicidi.bigdota.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class MapToPairJob extends AbstractSparkJob<JavaRDD, JavaPairRDD> {

    @Override
    JavaPairRDD<String, Integer> process(JavaRDD javaRDD, PipelineContext pipelineContext) {
        JavaPairRDD<String, Integer> pairs = javaRDD.mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1));
        return pairs;
    }
}
