package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

public class GameModeStrategy extends AbstractConvertStrategy<String, GameModeEnum, Object, DotaAnalyticsfield> {


    public GameModeStrategy(DotaAnalyticsfield fieldName, AbstractConvertStrategy... abstractConvertStrategy) {
        super(fieldName, abstractConvertStrategy);
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected GameModeEnum process(String rawData, Object o) {
        Integer result = MatchReplayUtil.getGame_Mode(rawData);
        GameModeEnum gameModeEnum = GameModeEnum.getEnum(result);
        Boolean canPick = MatchReplayUtil.getPick_Ban(rawData) != null;
        MatchReplayUtil.addGameMode(gameModeEnum.name(), canPick);
        return gameModeEnum;
    }
}
