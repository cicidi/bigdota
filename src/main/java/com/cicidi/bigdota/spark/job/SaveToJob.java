package com.cicidi.bigdota.spark.job;

import com.cicidi.bigdota.spark.PipelineContext;
import com.cicidi.bigdota.util.EnvConfig;
import org.apache.spark.api.java.JavaRDD;

public class SaveToJob<T> extends AbstractSparkJob<JavaRDD, Object> {

    @Override
    Object process(JavaRDD javaRDD, PipelineContext pipelineContext) {
        javaRDD.saveAsTextFile(EnvConfig.outputPath);
        return null;
    }
}
