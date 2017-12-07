package com.cicidi.framework.spark.db;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class FileSystemDataSource extends DataSource {

    private String path;

    public FileSystemDataSource(String path) {
        super(DataSourceType.FILESYSTEM);
        assertNotNull(path);
    }

    public String getPath() {
        return path;
    }
}
