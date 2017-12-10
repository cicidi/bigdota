package com.cicidi.framework.spark.db;


public class FileSystemDataSource extends DataSource {

    private String path;

    public FileSystemDataSource(String path) {
        super(DataSourceType.FILESYSTEM);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
