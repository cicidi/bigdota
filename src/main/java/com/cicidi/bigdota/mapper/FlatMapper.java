package com.cicidi.bigdota.mapper;

import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.cicidi.bigdota.spark.PipelineContext;
import com.cicidi.bigdota.util.MatchReplayUtil;

import java.util.Iterator;

public class FlatMapper extends Mapper<MatchReplayView, Iterator<String>> {

    public FlatMapper(PipelineContext pipelineContext) {
        super(FlatMapper.class.getSimpleName());
    }

    @Override
    public Iterator<String> map(PipelineContext pipelineContext, MatchReplayView matchReplayView) {
        return MatchReplayUtil.combine(matchReplayView, pipelineContext,accumulatorName);
    }
}

