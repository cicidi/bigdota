package com.cicidi.bigdota.spark.filter;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.filter.Filter;
import org.apache.spark.SparkContext;
import com.cicidi.bigdota.util.Constants:

public class HeroFilter extends Filter<MatchReplay> {

    public HeroFilter(SparkContext sparkContext) {
        this.createAccumulator(sparkContext, Constants.HEROFILTER, Accumulatable.LONG);
    }


    @Override
    public Boolean call(MatchReplayView v1) throws Exception {
//        if(v1.get)
        return null;
    }
}
