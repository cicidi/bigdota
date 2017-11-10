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
        if (result != null)
            return GameModeEnum.getEnum(result);
        if (MatchReplayUtil.getPick_Ban(rawData) != null) {
            return GameModeEnum.MODE_23;
        }
        return null;
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 50; i++) {
//            System.out.println(" MODE_" + i + "(" + i + "),");
//        }
//    }
}
