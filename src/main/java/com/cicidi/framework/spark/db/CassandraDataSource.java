package com.cicidi.framework.spark.db;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class CassandraDataSource extends DataSource {

    private String contactpoints;
    private String keyspace;
    private String tableName;

    public CassandraDataSource(String contactpoints, String keyspace, String tableName) {
        super(DataSourceType.CASSANDRA);
        assertNotNull(contactpoints);
        assertNotNull(keyspace);
        assertNotNull(tableName);
        this.contactpoints = contactpoints;
        this.keyspace = keyspace;
        this.tableName = tableName;
    }

    public String getContactpoints() {
        return contactpoints;
    }

    public void setContactpoints(String contactpoints) {
        this.contactpoints = contactpoints;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    //    public void connect(PipelineContext pipelineContext) {
//        pipelineContext.getSparkContext().getConf().set("spark.cassandra.connection.host", contactpoints);
//    }
}
