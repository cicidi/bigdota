package com.cicidi.bigdota.converter.strategy;

import com.cicidi.bigdota.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

public class GameModeStrategy extends AbstractConvertStrategy<String, GameModeEnum, Object, DotaAnalyticsfield> {


//    public GameModeStrategy(DotaAnalyticsfield fieldName, AbstractConvertStrategy... abstractConvertStrategy) {
//        super(fieldName, abstractConvertStrategy);
//    }

    private GameModeStrategy(Builder builder) {
        super();
        fieldName = builder.fieldName;
        successors = builder.successors;
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


    public static final class Builder {
        private DotaAnalyticsfield fieldName;
        private AbstractConvertStrategy[] successors;

        public Builder() {
        }

        public Builder fieldName(DotaAnalyticsfield val) {
            fieldName = val;
            return this;
        }

        public Builder successors(AbstractConvertStrategy... val) {
            successors = val;
            return this;
        }

        public GameModeStrategy build() {
            return new GameModeStrategy(this);
        }
    }
}
