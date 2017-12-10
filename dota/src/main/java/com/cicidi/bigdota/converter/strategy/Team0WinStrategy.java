package com.cicidi.bigdota.converter.strategy;

import com.cicidi.framework.spark.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.domain.dota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

public class Team0WinStrategy extends AbstractConvertStrategy<String, Boolean, GameModeEnum, DotaAnalyticsfield> {

    private Team0WinStrategy(Builder builder) {
        fieldName = builder.fieldName;
        successors = builder.successors;
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected Boolean process(String rawData, GameModeEnum gameModeEnum) {
        return MatchReplayUtil.getMatchResult(rawData);
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

        public Team0WinStrategy build() {
            return new Team0WinStrategy(this);
        }
    }
}
