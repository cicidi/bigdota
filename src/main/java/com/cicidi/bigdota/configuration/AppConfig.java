package com.cicidi.bigdota.configuration;

import com.cicidi.bigdota.cassandra.CassandraConnection;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import com.cicidi.bigdota.spark.SparkJob;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class AppConfig {

    @Bean
    public CassandraConnection cassandraConnector() {
        return new CassandraConnection();
    }

    @Bean
    public DotaReplayApi steamClient() {
        return new DotaReplayApi();
    }

    @Bean
    public SparkCassandraConnector sparkCassandraConnector() {
        return new SparkCassandraConnector();
    }

    @Bean
    public MatchReplayManagement matchRepalyManagement() {
        return new MatchReplayManagement();
    }

    @Bean
    public SparkJob sparkJob() {
        return new SparkJob();
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Client client() {
        return Client.create();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}