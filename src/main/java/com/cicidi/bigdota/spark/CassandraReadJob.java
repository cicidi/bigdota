package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.mapper.Mapper;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

public class CassandraReadJob<CassandraRow, T> extends AbstractSparkJob<JavaRDD, JavaRDD> {
    private static final Logger logger = Logger.getLogger(SparkCassandraConnector.class);
    private Mapper<com.datastax.spark.connector.japi.CassandraRow, T> mapper;
    private String keyspace;
    private String tableName;

    public CassandraReadJob(Mapper<com.datastax.spark.connector.japi.CassandraRow, T> mapper, String keyspace, String tableName) {
        this.mapper = mapper;
        this.keyspace = keyspace;
        this.tableName = tableName;
    }

    @Override
    JavaRDD<T> process(JavaRDD javaRDD, PipelineContext pipelineContext) {
        JavaRDD<T> cassandraRowsRDD = javaFunctions(pipelineContext.getSparkContext()).cassandraTable(keyspace, tableName)
                .map((Function<com.datastax.spark.connector.japi.CassandraRow, T>) cassandraRow -> mapper.map(pipelineContext, cassandraRow));
        return cassandraRowsRDD;
    }

}
