package com.cicidi.bigdota.spark;

import com.cicidi.bigdota.mapper.FlatMapper;
import com.cicidi.bigdota.mapper.Mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pipeline implements Serializable {

    private PipelineContext pipelineContext;
    private List<Pipeline> sparkJobs = new ArrayList<Pipeline>();

    public Pipeline(PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

//    public Pipeline addStep(AbstractSparkJob job) {
//        this.sparkJobs.add(job);
//        return this;
//    }

    public Pipeline read(DataSource dataSource, Mapper mapper) {
        this.sparkJobs.add(new ReadPipeline(pipelineContext, dataSource, mapper));
        return this;
    }

    public Pipeline filter(Filter filter) {
        this.sparkJobs.add(new FilterPipeline(pipelineContext, filter));
        return this;
    }

    public Pipeline flapmap(FlatMapper flatMapper) {
        this.sparkJobs.add(new FlatmapPipeline(pipelineContext, flatMapper));
        return this;
    }

    public Pipeline reduce() {
        this.sparkJobs.add(new ReducePipeline(pipelineContext));
        return this;
    }

    public Pipeline sort(Comparator comparator) {
        this.sparkJobs.add(new SortPipeline(pipelineContext, comparator));
        return this;
    }

    public Pipeline write(DataSource dataSource) {
        this.sparkJobs.add(new WritePipeline(pipelineContext, dataSource));
        return this;
    }
//    read cassandra keyspace tabble  mapper
//    flatmap mapper
//    readuce
//    sort (comparat)
//    write(path)

}
