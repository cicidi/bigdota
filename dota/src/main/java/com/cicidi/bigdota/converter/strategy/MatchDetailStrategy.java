package com.cicidi.bigdota.converter.strategy;

import com.cicidi.framework.spark.converter.AbstractConvertStrategy;
import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.domain.dota.ruleEngine.GameModeEnum;
import com.cicidi.bigdota.util.MatchReplayUtil;

import java.util.Map;

public class MatchDetailStrategy extends AbstractConvertStrategy<String, Map<DotaAnalyticsfield, Object>, GameModeEnum, DotaAnalyticsfield> {

    private MatchDetailStrategy(Builder builder) {
        fieldName = builder.fieldName;
        successors = builder.successors;
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }

    @Override
    protected Map<DotaAnalyticsfield, Object> process(String rawData, GameModeEnum gameModeEnum) {
//        switch (gameModeEnum) {
//            case MODE_20:
//                return MatchReplayUtil.getHeros(rawData);
//            case MODE_22:
//                return MatchReplayUtil.getHeros_normalModel(rawData);
//            default:
//                return MatchReplayUtil.getHeros_normalModel(rawData);
//        }

        Map<DotaAnalyticsfield, Object> map = MatchReplayUtil.getMatchDetails(rawData);

        if (map == null || map.get(DotaAnalyticsfield.HERO_PICK) == null) {
            map = MatchReplayUtil.getHeros_normalModel(rawData);
        }
        return map;
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

        public Builder successors(AbstractConvertStrategy[] val) {
            successors = val;
            return this;
        }

        public MatchDetailStrategy build() {
            return new MatchDetailStrategy(this);
        }
    }
}
