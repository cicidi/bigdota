package com.cicidi.bigdota.spark.job;

import com.cicidi.bigdota.spark.PipelineContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function2;

public class ReduceJob extends AbstractSparkJob<JavaPairRDD, JavaPairRDD> {
    @Override
    JavaPairRDD<String, Integer> process(JavaPairRDD javaRDD, PipelineContext pipelineContext) {
        JavaPairRDD<String, Integer> pairs = javaRDD.reduceByKey((Function2<Integer, Integer, Integer>) (a, b) -> a + b);
        return pairs;
    }

}
