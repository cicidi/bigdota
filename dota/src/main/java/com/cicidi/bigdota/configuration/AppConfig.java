package com.cicidi.bigdota.configuration;

import com.cicidi.bigdota.extermal.DotaReplayApi;
import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.sun.jersey.api.client.Client;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class AppConfig {


    @Bean
    public DotaReplayApi dotaReplayApi() {
        return new DotaReplayApi();
    }

    @Value("${app.name}")
    private String appName;

    @Value("${env}")
    private String env;

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf;
        if (env.equals("dev"))
            sparkConf = new SparkConf().setAppName(appName).setMaster("local")
                    .set("spark.driver.maxResultSize", "14g");
        else {
            sparkConf = new SparkConf().setAppName(appName)
                    .setMaster("spark://ubuntu03:7077")
                    .set("spark.cassandra.connection.keep_alive_ms", "30000")
                    .set("spark.driver.maxResultSize", "14g");
        }
        return sparkConf;
    }

    @Bean
    public SparkContext sparkContext() {
        SparkContext sparkContext = new SparkContext(sparkConf());
        return sparkContext;
    }

    @Bean
    public MatchReplayManagement matchRepalyManagement() {
        return new MatchReplayManagement();
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