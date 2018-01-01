package com.cicidi.bigdota.spark.filter;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.filter.Filter;
import org.apache.spark.SparkContext;


public class HeroFilter extends Filter<MatchReplay> {


    public HeroFilter(SparkContext sparkContext) {
        super();
        this.accumulatorV2 = this.createAccumulator(sparkContext, "HeroFilter", Accumulatable.LONG);
    }

    @Override
    public Boolean call(MatchReplay v1) throws Exception {
        this.accumulate(1L);
        return null;
    }
}
