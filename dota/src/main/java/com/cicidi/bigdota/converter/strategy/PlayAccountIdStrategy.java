//package com.cicidi.bigdota.converter.strategy;
//
//import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
//import com.cicidi.bigdota.domain.dota.ruleEngine.GameModeEnum;
//import com.cicidi.bigdota.util.MatchReplayUtil;
//import com.cicidi.framework.spark.converter.AbstractConvertStrategy;
//
//import java.util.Map;
//
//public class PlayAccountIdStrategy extends AbstractConvertStrategy<String, Map<DotaAnalyticsfield, Object>, GameModeEnum, DotaAnalyticsfield> {
//
//    private PlayAccountIdStrategy(Builder builder) {
//        fieldName = builder.fieldName;
//        successors = builder.successors;
//    }
//
//    @Override
//    protected boolean isEnabled() {
//        return false;
//    }
//
//    @Override
//    protected Map<DotaAnalyticsfield, Object> process(String rawData, GameModeEnum gameModeEnum) {
//
//        Map<DotaAnalyticsfield, Object> map = MatchReplayUtil.getPlayerAccountId(rawData);
//        return map;
//    }
//
//    public static final class Builder {
//        private DotaAnalyticsfield fieldName;
//        private AbstractConvertStrategy[] successors;
//
//        public Builder() {
//        }
//
//        public Builder fieldName(DotaAnalyticsfield val) {
//            fieldName = val;
//            return this;
//        }
//
//        public Builder successors(AbstractConvertStrategy[] val) {
//            successors = val;
//            return this;
//        }
//
//        public PlayAccountIdStrategy build() {
//            return new PlayAccountIdStrategy(this);
//        }
//    }
//}
