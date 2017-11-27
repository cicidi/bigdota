package com.cicidi.bigdota.spark.job;

import com.cicidi.bigdota.mapper.Mapper;
import com.cicidi.bigdota.spark.PipelineContext;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Iterator;

public class FlatMapJob<IN, OUT> extends AbstractSparkJob<JavaRDD, JavaRDD> {
    private static final Logger logger = Logger.getLogger(SparkCassandraConnector.class);
    private Mapper<IN, Iterator> mapper;


    @Override
    JavaRDD<String> process(JavaRDD javaRDD, PipelineContext pipelineContext) {
        JavaRDD<String> combo = javaRDD.flatMap((FlatMapFunction<IN, String>) input -> mapper.map(pipelineContext, input));
        return combo;
    }

}
