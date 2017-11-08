package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.domain.dota.DotaMatchField;
import com.cicidi.bigdota.domain.dota.GameModeEnum;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.util.MatchReplayUtil;
import scala.Int;

public class GameModeStrategy extends AbstractConvertStrategy<String, GameModeEnum, Object, DotaMatchField> {


    public GameModeStrategy(DotaMatchField fieldName, AbstractConvertStrategy... abstractConvertStrategy) {
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

}
