package com.cicidi.framework.spark.db;

import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;

public abstract class SparkRepository {


    protected DataSource dataSource;

    protected Class classType;

    public abstract void save(SparkContext sparkContext, JavaRDD javaRDD);

    public abstract JavaRDD fetchAll(SparkContext sparkContext, Mapper mapper);

    public SparkRepository(DataSource dataSource, Class classType) {
        this.dataSource = dataSource;
        this.classType = classType;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Class getClassType() {
        return classType;
    }
}
