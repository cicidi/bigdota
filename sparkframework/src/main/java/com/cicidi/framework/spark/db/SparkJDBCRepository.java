package com.cicidi.framework.spark.db;

import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.Properties;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class SparkJDBCRepository<T> extends SparkRepository {

    private Mapper mapper;
    private StructType schema;

    public SparkJDBCRepository(DataSource dataSource, Class classType, StructType schema, Mapper mapper) {
        super(dataSource, classType);
        this.mapper = mapper;
        this.schema = schema;
    }

    @Override
    public void save(SparkContext sparkContext, AbstractJavaRDDLike javaRDD) {
        SQLContext sqlcontext = new SQLContext(sparkContext);
        JavaRDD<Row> rowRDD = javaRDD.map(mapper);
        Dataset<Row> outDataFrame = sqlcontext.createDataFrame(rowRDD, this.schema);
        JDBCDataSource jdbcDataSource = (JDBCDataSource) dataSource;
        Properties prop = new java.util.Properties();
        prop.setProperty("database", jdbcDataSource.getDatabase());
        prop.setProperty("user", jdbcDataSource.getUsername());
        prop.setProperty("password", jdbcDataSource.getPassword());
        prop.setProperty("driver", jdbcDataSource.getDriver());
        outDataFrame.write().mode(SaveMode.Overwrite).jdbc("jdbc:postgresql://" + jdbcDataSource.getUrl() + ":" + jdbcDataSource.getPort(), jdbcDataSource.getTableName(), prop);
    }

    @Override
    public JavaRDD fetchAll(SparkContext sparkContext, Mapper mapper) {
//        LongAccumulator accumulator = sparkContext.longAccumulator(AccumulatorConstants.MATCH);
//        JavaRDD<T> cassandraRowsRDD = javaFunctions(sparkContext)
//                .cassandraTable(((JDBCDataSource) (this.dataSource)).getKeyspace(),
//                        ((JDBCDataSource) (this.dataSource)).getTableName())
//                .map(mapper);
//        return cassandraRowsRDD;
        return null;
    }
}
