package com.cicidi.bigdota.spark.jobConfig;

import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.converter.strategy.GameModeStrategy;
import com.cicidi.bigdota.converter.strategy.MatchDetailStrategy;
import com.cicidi.bigdota.converter.strategy.Team0WinStrategy;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.domain.dota.ruleEngine.BestHeroCombination;
import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.spark.TupleComparator;
import com.cicidi.bigdota.spark.mapper.BestCombinationMapper;
import com.cicidi.bigdota.spark.mapper.MatchReplayMapper;
import com.cicidi.bigdota.spark.mapper.MatchReplayViewMapper;
import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.MatchReplayUtil;
import com.cicidi.framework.spark.converter.AbstractConverter;
import com.cicidi.framework.spark.db.*;
import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Configuration
public class HeroDraftJobComponent implements Serializable {
    /* repository config*/
    @Value("${cassandra.contactpoints}")
    private String contactpoints;


    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${out.path}")
    private String path;

    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.port}")

    private String jdbcPort;

    @Value("${jdbc.userName}")
    private String jdbcUserName;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.database}")
    private String jdbcDatabase;

    @Value("${jdbc.tableName}")
    private String jdbcTableName;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    private String table = Constants.REPLAY_TABLE;

    @Autowired
    private SparkContext sparkContext;

    @Bean
    public DataSource cassandraDataSource() {
        return new CassandraDataSource(contactpoints, keyspace, table);
    }

    @Bean
    public DataSource jdbcDataSource() {
        return new JDBCDataSource(jdbcUrl, jdbcPort, jdbcUserName, jdbcPassword, jdbcDatabase, jdbcTableName, jdbcDriver);
    }

    @Bean
    public DataSource fileSystemDataSource() {
        return new FileSystemDataSource(path);
    }

    @Bean(name = "sparkCassandraRepository_heroDraftJob")
    public SparkRepository sparkCassandraRepository_heroDraftJob() {
        return new SparkCassandraRepository(cassandraDataSource(), MatchReplay.class);
    }

    @Bean(name = "sparkJdbcRepository_heroDraftJob")
    public SparkRepository sparkJdbcRepository_heroDraftJob() {
        return new SparkJDBCRepository(jdbcDataSource(), BestHeroCombination.class, createSchema(), bestCombinationMapper());
    }

    @Bean(name = "sparkFileSystemRepository__heroDraftJob")
    public SparkRepository sparkFileSystemRepository__heroDraftJob() {
        return new SparkFileSystemRepository(fileSystemDataSource(), null);
    }

    @Bean(name = "matchConverter_heroDraftJob")
    public AbstractConverter matchConverter() {
        MatchDetailStrategy heroPickStrategy = new MatchDetailStrategy.Builder().fieldName(DotaAnalyticsfield.MATCH_DETAIL).build();
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


    @Bean(name = "bestCombinationMapper")
    public Mapper bestCombinationMapper() {
        return new BestCombinationMapper(sparkContext);
    }

    @Bean(name = "heroFilter")
    public Function<MatchReplayView, Boolean> heroFilter() {
        int hero_Id = 108;
        return (Function<MatchReplayView, Boolean>) matchReplayView -> {
            if (matchReplayView == null)
                return false;
            List<Integer> team0 = matchReplayView.getTeam_hero(0);
            team0 = team0 != null ? team0 : new ArrayList<>();
            List<Integer> team1 = matchReplayView.getTeam_hero(1);
            team1 = team1 != null ? team1 : new ArrayList<>();

            return team0.contains(hero_Id) || team1.contains(hero_Id);
        };
    }

    @Bean(name = "playerFilter")
    public Function<MatchReplayView, Boolean> playerFilter() {
        int account_id = 151018536;
        return (Function<MatchReplayView, Boolean>) matchReplayView -> {
            if (matchReplayView == null)
                return false;
            List<Integer> team0 = matchReplayView.getTeamPlayer(0);
            team0 = team0 != null ? team0 : new ArrayList<>();
            List<Integer> team1 = matchReplayView.getTeamPlayer(1);
            team1 = team1 != null ? team1 : new ArrayList<>();

            return team0.contains(account_id) || team1.contains(account_id);
        };
    }


    @Bean(name = "flatMapFunction_heroDraftJob_MatchReplayView")
    public FlatMapFunction<MatchReplayView, String> flatMapFunction() {
        return (FlatMapFunction<MatchReplayView, String>) matchReplayView -> MatchReplayUtil.combine(matchReplayView);
    }


    @Bean(name = "comparator__heroDraftJob_count")
    public Comparator<Tuple2<String, Integer>> comparator() {
        return new TupleComparator(sparkContext);
    }

    public StructType createSchema() {
        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("combination", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("total", DataTypes.IntegerType, true));
        StructType schema = DataTypes.createStructType(fields);
        return schema;
    }
}
