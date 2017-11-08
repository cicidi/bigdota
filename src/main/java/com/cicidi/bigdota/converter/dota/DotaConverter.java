package com.cicidi.bigdota.converter.dota;

import com.cicidi.bigdota.converter.AbstractConverter;
import com.cicidi.bigdota.converter.strategy.GameModeStrategy;
import com.cicidi.bigdota.converter.strategy.HeroPickStrategy;
import com.cicidi.bigdota.domain.dota.DotaMatchField;

public class DotaConverter extends AbstractConverter<String> {

    public DotaConverter(String input) {
        super(input);
    }

    @Override
    protected void init() {
        HeroPickStrategy team_0_hero_pick = new HeroPickStrategy(DotaMatchField.TEAM_0_HERO_PICK, null);
        HeroPickStrategy team_1_hero_pick = new HeroPickStrategy(DotaMatchField.TEAM_1_HERO_PICK, null);
//        HeroPickStrategy team_0_players = new HeroPickStrategy(DotaMatchField.TEAM_0_PLAYERS, null);
//        HeroPickStrategy team_1_players = new HeroPickStrategy(DotaMatchField.TEAM_1_PLAYERS, null);
//        GameModeStrategy gameModeStrategy = new GameModeStrategy(DotaMatchField.GAME_MODE, team_0_hero_pick, team_1_hero_pick, team_0_players, team_1_players);
        GameModeStrategy gameModeStrategy = new GameModeStrategy(DotaMatchField.GAME_MODE, team_0_hero_pick, team_1_hero_pick);
        this.addStrategy(gameModeStrategy);

    }

    @Override
    protected void validate() {

    }
}
