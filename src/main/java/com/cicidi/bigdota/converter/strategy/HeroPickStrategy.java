package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

import java.util.Map;

public class HeroPickStrategy extends AbstractConvertStrategy<String, Map<DotaAnalyticsfield, Object>, GameModeEnum, DotaAnalyticsfield> {


    public HeroPickStrategy(DotaAnalyticsfield fieldName, AbstractConvertStrategy abstractConvertStrategy) {
        super(fieldName, abstractConvertStrategy);
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected Map<DotaAnalyticsfield, Object> process(String rawData, GameModeEnum gameModeEnum) {
        switch (gameModeEnum) {
            case MODE_20:
                return MatchReplayUtil.getHeros(rawData);
            case MODE_22:
                return MatchReplayUtil.getHeros_normalModel(rawData);
            default:
                return MatchReplayUtil.getHeros(rawData);
        }
    }
}
