package com.cicidi.bigdota.configuration;

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
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Configuration
@EnableRetry
public class AppConfig {


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
    public WebTarget getWebTarget() {
        return ClientBuilder.newClient()
                .target("https://api.opendota.com/api/matches");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(20000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(new DefaultListenerSupport());
        return retryTemplate;
    }
}