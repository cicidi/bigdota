package com.cicidi.framework.spark.db;

import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
    Map<String, String> config;
    DataSource.DataSourceType dataSourceType;


    private DataSourceFactory(Builder builder) {
        config = builder.config;
        dataSourceType = builder.dataSourceType;
    }

    public static final class Builder {
        private Map<String, String> config;
        DataSource.DataSourceType dataSourceType;

        public Builder() {
        }

        public Builder set(String key, String value) {
            if (config == null) {
                config = new HashMap<>();
            }
            this.config.put(key, value);
            return this;
        }


        public Builder dataSourceType(DataSource.DataSourceType val) {
            dataSourceType = val;
            return this;
        }

        public DataSourceFactory build() {
            return new DataSourceFactory(this);
        }
    }
}
