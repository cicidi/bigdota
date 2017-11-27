package com.cicidi.bigdota.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pipeline implements Serializable {

    private PipelineContext pipelineContext;
    private List<AbstractSparkJob> sparkJobs = new ArrayList<>();

    public Pipeline(PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

    public Pipeline addStep(AbstractSparkJob job) {
        this.sparkJobs.add(job);
        return this;
    }
}
