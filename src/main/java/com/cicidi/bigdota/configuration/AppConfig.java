package com.cicidi.bigdota.configuration;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.extermal.DotaReplayApi;
import com.cicidi.bigdota.service.dota.MatchReplayManagement;
import com.cicidi.bigdota.spark.SparkCassandraConnector;
import com.cicidi.bigdota.spark.SparkJob;
import com.cicidi.bigdota.util.EnvConfig;
import com.cicidi.bigdota.validator.MatchDataValidator;
import com.cicidi.validation.Validator;
import com.sun.jersey.api.client.Client;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class AppConfig {


    @Bean
    public DotaReplayApi dotaReplayApi() {
        return new DotaReplayApi();
    }

    @Bean
    public SparkCassandraConnector sparkCassandraConnector() {
        return new SparkCassandraConnector(sparkContext());
    }

    //
    @Value("${env.runtime}")
    private String runtime;

    @Value("${cassandra.contactpoints}")
    private String cassandraIps;

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf;
        if (runtime.equals("local"))
            sparkConf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", cassandraIps).set("spark.driver.maxResultSize", "14g");
        else {
            sparkConf = new SparkConf().setAppName("bigdota").setMaster("spark://ubuntu03:7077")
                    .set("spark.cassandra.connection.host", EnvConfig.CASSANDRA_IP)
                    .set("spark.cassandra.connection.keep_alive_ms", "30000")
                    .set("spark.driver.maxResultSize", "14g");
        }
//        SparkConf sparkConf = new SparkConf().setAppName("bigdota").setMaster("local").set("spark.cassandra.connection.host", EnvConfig.CASSANDRA_IP).set("spark.driver.maxResultSize", "14g");

//        String[] jars = new String[]{
//                "/Users/cicidi/.m2/repository/com/datastax/spark/spark-cassandra-connector_2.11/2.0.5/spark-cassandra-connector_2.11-2.0.5.jar",
//                "/Users/cicidi/.m2/repository/com/datastax/cassandra/cassandra-driver-core/3.3.0/cassandra-driver-core-3.3.0.jar",
//                "/Users/cicidi/.m2/repository/org/apache/spark/spark-core_2.11/2.2.0/spark-core_2.11-2.2.0.jar",
//                "/Volumes/WD/project/bigdota/target/bigdota-1.0-SNAPSHOT.jar"
//        };
//        sparkConf.setJars(jars);
        return sparkConf;
    }

    @Bean
    public SparkContext sparkContext() {
        SparkContext sparkContext = new SparkContext(sparkConf());
//        sparkContext.addJar("/Users/cicidi/.m2/repository/com/datastax/spark/spark-cassandra-connector_2.11/2.0.5/spark-cassandra-connector_2.11-2.0.5.jar");
//        sparkContext.addJar("/Users/cicidi/.m2/repository/com/datastax/cassandra/cassandra-driver-core/3.3.0/cassandra-driver-core-3.3.0.jar");
//        sparkContext.addJar("/Users/cicidi/.m2/repository/org/apache/spark/spark-core_2.11/2.2.0/spark-core_2.11-2.2.0.jar");
//        sparkContext.addJar("/Volumes/WD/project/bigdota/target/bigdota-1.0-SNAPSHOT.jar");
        return sparkContext;
    }

    @Bean
    public MatchReplayManagement matchRepalyManagement() {
        return new MatchReplayManagement();
    }

    @Bean
    public SparkJob sparkJob() {
        return new SparkJob();
    }


//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }

    @Bean
    public Client client() {
        return Client.create();
    }

//    @Bean
//    public WebTarget getWebTarget() {
//        return ClientBuilder.newClient()
//                .target("https://api.opendota.com/api/matches");
//    }

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

    @Bean
    public Validator<MatchReplay> validator() {
        return new MatchDataValidator();
    }
}