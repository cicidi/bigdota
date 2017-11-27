package com.cicidi.bigdota.spark;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class CassandraWriteJob<T> extends AbstractSparkJob<JavaRDD, JavaPairRDD> {
    private static final Logger logger = Logger.getLogger(SparkCassandraConnector.class);
    private Class<T> dtoClass;
    private String keyspace;
    private String tableName;

    public CassandraWriteJob(Class<T> clazzAlert, String keyspace, String tableName) {
        this.dtoClass = clazzAlert;
        this.keyspace = keyspace;
        this.tableName = tableName;
    }

    @Override
    JavaPairRDD<String, Integer> process(JavaRDD javaRDD, PipelineContext pipelineContext) {
        javaFunctions(javaRDD).writerBuilder(keyspace, tableName, mapToRow(dtoClass)).saveToCassandra();
        return null;
    }

}
