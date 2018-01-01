package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.spark.filter.HeroFilter;
import com.cicidi.bigdota.spark.mapper.MatchReplayMapper;
import com.cicidi.bigdota.spark.mapper.MatchReplayViewMapper;
import com.cicidi.framework.spark.converter.AbstractConverter;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.converter.strategy.GameModeStrategy;
import com.cicidi.bigdota.converter.strategy.HeroPickStrategy;
import com.cicidi.bigdota.converter.strategy.Team0WinStrategy;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.framework.spark.db.*;
import com.cicidi.framework.spark.filter.Filter;
import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

@Configuration
public class HeroDraftJobConfig implements Serializable {
    /* repository config*/
    @Value("${cassandra.contactpoints}")
    private String contactpoints;


    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${out.path}")
    private String path;

    private String table = Constants.REPLAY_TABLE;

    @Autowired
    private SparkContext sparkContext;

    @Bean
    public DataSource cassandraDataSource() {
        return new CassandraDataSource(contactpoints, keyspace, table);
    }

    @Bean
    public DataSource fileSystemDataSource() {
        return new FileSystemDataSource(path);
    }

    @Bean(name = "sparkCassandraRepository_heroDraftJob")
    public SparkRepository sparkCassandraRepository_heroDraftJob() {
        return new SparkCassandraRepository(cassandraDataSource(), MatchReplay.class);
    }

    @Bean(name = "sparkFileSystemRepository__heroDraftJob")
    public SparkRepository sparkFileSystemRepository__heroDraftJob() {
        return new SparkFileSystemRepository(fileSystemDataSource(), null);
    }

    @Bean(name = "matchConverter_heroDraftJob")
    public AbstractConverter matchConverter() {
        HeroPickStrategy heroPickStrategy = new HeroPickStrategy.Builder().fieldName(DotaAnalyticsfield.HERO_PICK).build();
        Team0WinStrategy team_0_win = new Team0WinStrategy.Builder().fieldName(DotaAnalyticsfield.TEAM_0_WIN).build();
        GameModeStrategy gameModeStrategy = new GameModeStrategy.Builder().fieldName(DotaAnalyticsfield.GAME_MODE).successors(heroPickStrategy, team_0_win).build();
        DotaConverter dotaConverter = new DotaConverter();
        dotaConverter.addStrategy(gameModeStrategy);
        return dotaConverter;
    }


    @Bean(name = "matchReplayMapper_heroDraftJob")
    public Mapper matchReplayMapper() {
        return new MatchReplayMapper(sparkContext, matchConverter());
    }

    @Bean(name = "matchReplayViewMapper_heroDraftJob")
    public Mapper matchReplayViewMapper() {
        return new MatchReplayViewMapper(sparkContext);
    }


    @Bean(name = "matchReplayViewMapper_heroDraftJob")
    public Filter heroFilter() {
        return new HeroFilter(sparkContext);
    }

    @Bean(name = "flatMapFunction_heroDraftJob_MatchReplayView")
    public FlatMapFunction<MatchReplayView, String> flatMapFunction() {
        return (FlatMapFunction<MatchReplayView, String>) matchReplayView -> MatchReplayUtil.combine(matchReplayView, null, "COUNT");
    }


    @Bean(name = "comparator__heroDraftJob_count")
    public Comparator<Tuple2<String, Integer>> comparator() {
        return new TupleComparator(sparkContext);
    }
}
