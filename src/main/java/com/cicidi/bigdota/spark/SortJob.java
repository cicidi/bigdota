package com.cicidi.bigdota.spark;

import org.apache.spark.api.java.JavaPairRDD;

import java.util.List;

public class SortJob extends AbstractSparkJob<JavaPairRDD, List> {

    @Override
    List process(JavaPairRDD javaRDD, PipelineContext pipelineContext) {
        return javaRDD.takeOrdered(1000, SparkJob.MyTupleComparator.INSTANCE);
    }
}
