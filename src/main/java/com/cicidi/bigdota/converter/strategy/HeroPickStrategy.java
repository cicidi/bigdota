package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.domain.dota.DotaMatchField;
import com.cicidi.bigdota.domain.dota.GameModeEnum;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.util.MatchReplayUtil;

public class HeroPickStrategy extends AbstractConvertStrategy<String, int[], GameModeEnum, DotaMatchField> {


    public HeroPickStrategy(DotaMatchField fieldName, AbstractConvertStrategy abstractConvertStrategy) {
        super(fieldName, abstractConvertStrategy);
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected int[] process(String rawData, GameModeEnum gameModeEnum) {
        if (gameModeEnum.equals(GameModeEnum.MODE_22)) {
            if (this.fieldName.equals(DotaMatchField.TEAM_0_HERO_PICK))
                return MatchReplayUtil.getHeros(rawData, 0);
            if (this.fieldName.equals(DotaMatchField.TEAM_1_HERO_PICK))
                return MatchReplayUtil.getHeros(rawData, 1);
        }
        return null;
    }
}
