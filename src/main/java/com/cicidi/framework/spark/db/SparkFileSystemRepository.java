package com.cicidi.framework.spark.db;

import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;

import java.io.File;
import java.io.IOException;

public class SparkFileSystemRepository extends SparkRepository {

    public SparkFileSystemRepository(DataSource dataSource, Class classType) {
        super(dataSource, classType);
    }

    @Override
    public void save(SparkContext sparkContext, JavaRDD javaRDD) {
        try {
            String path = ((FileSystemDataSource) this.dataSource).getPath();
            FileUtils.deleteDirectory(new File(path));
            javaRDD.saveAsTextFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JavaRDD fetchAll(SparkContext sparkContext, Mapper mapper) {
        //TBD
        return null;
    }
}
