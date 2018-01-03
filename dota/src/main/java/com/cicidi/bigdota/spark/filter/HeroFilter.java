package com.cicidi.bigdota.spark.filter;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.filter.Filter;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.Function;


public class HeroFilter extends Filter<MatchReplayView> {


    public HeroFilter(SparkContext sparkContext) {
        super();
        this.accumulatorV2 = this.createAccumulator(sparkContext, "HeroFilter", Accumulatable.LONG);
    }

    @Override
    public Boolean call(MatchReplayView v1) throws Exception {
//        return (MatchReplayView) v1 -> {
//            if (v1.getTeam_hero(0).contains(1) || v1.getTeam_hero(1).contains(1))
//                this.accumulate(1L);
//            return Boolean.TRUE;
//            else return Boolean.FALSE;
//        };
//
        return null;
    }
}
