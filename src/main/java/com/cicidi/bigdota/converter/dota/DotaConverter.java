package com.cicidi.bigdota.converter.dota;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.converter.strategy.GameModeStrategy;
import com.cicidi.bigdota.converter.strategy.HeroPickStrategy;
import com.cicidi.bigdota.converter.strategy.Team0WinStrategy;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.util.JSONUtil;

import java.io.IOException;
import java.util.Map;

public class DotaConverter extends AbstractConverter<String> {

    public DotaConverter(String input) {
        super(input);
    }

    @Override
    protected void init() {
        HeroPickStrategy team_hero_pick = new HeroPickStrategy(DotaAnalyticsfield.HERO_PICK, null);
        Team0WinStrategy team_0_win = new Team0WinStrategy(DotaAnalyticsfield.TEAM_0_WIN, null);
        GameModeStrategy gameModeStrategy = new GameModeStrategy(DotaAnalyticsfield.GAME_MODE, team_hero_pick, team_0_win);
        this.addStrategy(gameModeStrategy);

    }

    @Override
    protected void validate() {

    }

}
