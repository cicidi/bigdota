package com.cicidi.framework.spark.db;

import org.apache.commons.lang.Validate;

public class JDBCDataSource extends DataSource {

    private final String url;
    private final String port;
    private final String username;
    private final String password;
    private final String database;
    private final String tableName;
    private final String driver;

    public JDBCDataSource(String url, String port, String username, String password, String database, String tableName, String driver) {
        super(DataSourceType.JDBC);
        Validate.notNull(url);
        Validate.notNull(port);
        Validate.notNull(username);
        Validate.notNull(password);
        Validate.notNull(database);
        Validate.notNull(tableName);
        Validate.notNull(driver);
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.tableName = tableName;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDriver() {
        return driver;
    }
}
