package com.cicidi.framework.spark.db;

import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class SparkCassandraRepository<T> extends SparkRepository {

    public SparkCassandraRepository(DataSource dataSource, Class classType) {
        super(dataSource, classType);
    }

    @Override
    public void save(SparkContext sparkContext, AbstractJavaRDDLike javaRDD) {
        sparkContext.getConf().set("spark.cassandra.connection.host", ((CassandraDataSource) (this.dataSource)).getContactpoints());
        javaFunctions((JavaRDD) javaRDD).
                writerBuilder(((CassandraDataSource) (this.dataSource)).getKeyspace(),
                        ((CassandraDataSource) (this.dataSource)).getTableName(),
                        mapToRow(this.classType))
                .saveToCassandra();

    }

    @Override
    public JavaRDD fetchAll(SparkContext sparkContext, Mapper mapper) {
        sparkContext.getConf().set("spark.cassandra.connection.host", ((CassandraDataSource) (this.dataSource)).getContactpoints());
//        LongAccumulator accumulator = sparkContext.longAccumulator(AccumulatorConstants.MATCH);
        JavaRDD<T> cassandraRowsRDD = javaFunctions(sparkContext)
                .cassandraTable(((CassandraDataSource) (this.dataSource)).getKeyspace(),
                        ((CassandraDataSource) (this.dataSource)).getTableName())
                .map(mapper);
        return cassandraRowsRDD;
    }
}
