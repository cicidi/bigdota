package com.cicidi.bigdota.configuration;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.converter.dota.DotaConverter;
import com.cicidi.bigdota.converter.strategy.GameModeStrategy;
import com.cicidi.bigdota.converter.strategy.HeroPickStrategy;
import com.cicidi.bigdota.converter.strategy.Team0WinStrategy;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.validator.MatchDataValidator;
import com.cicidi.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    @Bean
    public Validator<MatchReplay> validator() {
        return new MatchDataValidator();
    }

    @Bean
    public AbstractConverter dotaConverter() {
        HeroPickStrategy heroPickStrategy = new HeroPickStrategy.Builder().fieldName(DotaAnalyticsfield.HERO_PICK).build();
        Team0WinStrategy team_0_win = new Team0WinStrategy.Builder().fieldName(DotaAnalyticsfield.TEAM_0_WIN).build();
        GameModeStrategy gameModeStrategy = new GameModeStrategy.Builder().fieldName(DotaAnalyticsfield.GAME_MODE).successors(heroPickStrategy, team_0_win).build();
        DotaConverter dotaConverter = new DotaConverter();
        dotaConverter.addStrategy(gameModeStrategy);
        return dotaConverter;
    }
}
