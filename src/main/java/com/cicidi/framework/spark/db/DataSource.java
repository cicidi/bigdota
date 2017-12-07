package com.cicidi.framework.spark.db;

import java.util.HashMap;
import java.util.Map;

public class DataSource {
    protected Map<String, String> config;
    protected DataSourceType dataSourceType;

    private DataSource(Builder builder) {
        setConfig(builder.config);
        setDataSourceType(builder.dataSourceType);
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public DataSource(DataSourceType dataSourceType) {
        this.config = config;
        this.dataSourceType = dataSourceType;
    }

    public static final class Builder {
        private Map<String, String> config;
        private DataSourceType dataSourceType;

        public Builder() {
        }

        public Builder set(String key, String value) {
            if (config == null) {
                config = new HashMap<>();
            }
            this.config.put(key, value);
            return this;
        }


        public Builder dataSourceType(DataSourceType val) {
            dataSourceType = val;
            return this;
        }

        public DataSource build() {
            return new DataSource(this);
        }
    }

    public enum DataSourceType {
        FILESYSTEM, CASSANDRA, MYSQL, HDFS
    }
}
