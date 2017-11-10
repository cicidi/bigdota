package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

public class Team0WinStrategy extends AbstractConvertStrategy<String, Boolean, GameModeEnum, DotaAnalyticsfield> {


    public Team0WinStrategy(DotaAnalyticsfield fieldName, AbstractConvertStrategy abstractConvertStrategy) {
        super(fieldName, abstractConvertStrategy);
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected Boolean process(String rawData, GameModeEnum gameModeEnum) {
        return MatchReplayUtil.getMatchResult(rawData);
    }
}
