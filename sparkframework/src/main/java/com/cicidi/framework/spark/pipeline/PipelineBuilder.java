package com.cicidi.framework.spark.pipeline;

import com.cicidi.framework.spark.Filter;
import com.cicidi.framework.spark.db.SparkCassandraRepository;
import com.cicidi.framework.spark.db.SparkFileSystemRepository;
import com.cicidi.framework.spark.db.SparkRepository;
import com.cicidi.framework.spark.mapper.Mapper;
import com.cicidi.framework.spark.pipeline.impl.*;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PipelineBuilder<T> implements Serializable {

    private PipelineContext pipelineContext;
    private List<Pipeline> pipelineList = new ArrayList<Pipeline>();

    public PipelineBuilder(PipelineContext pipelineContext) {
        this.pipelineContext = pipelineContext;
    }

//    public Pipeline addStep(AbstractSparkJob job) {
//        this.sparkJobs.add(job);
//        return this;
//    }

    public PipelineBuilder readFrom(SparkRepository sparkRepository, Mapper mapper) {
        ReadPipeline readPipeline = null;
        switch (sparkRepository.getDataSource().getDataSourceType()) {
            case CASSANDRA:
                readPipeline = new CassandraReadPipeline<T>(pipelineContext, (SparkCassandraRepository) sparkRepository, mapper);
                break;
            case FILESYSTEM:
                readPipeline = new FileSystemReadPipeline<T>(pipelineContext, (SparkFileSystemRepository) sparkRepository, mapper);
                break;
            default:
                break;
        }
        pipelineList.add(readPipeline);
        return this;
    }

    public PipelineBuilder filter(Filter filter) {
        this.pipelineList.add(new FilterPipeline(pipelineContext, filter));
        return this;
    }

    public PipelineBuilder flapmap(FlatMapFunction flatMapFunction) {
        this.pipelineList.add(new FlatmapPipeline(pipelineContext, flatMapFunction));
        return this;
    }

    public PipelineBuilder mapToPair(Object v) {
        this.pipelineList.add(new MapToPairPipeline(pipelineContext, v));
        return this;
    }

    public PipelineBuilder reduceByKey() {
        this.pipelineList.add(new ReducePipeline(pipelineContext));
        return this;
    }

    public PipelineBuilder sortBy(Comparator comparator, int topN) {
        this.pipelineList.add(new SortPipeline(pipelineContext, comparator, topN));
        return this;
    }

    public PipelineBuilder saveTo(SparkRepository sparkRepository) {
        WritePipeline writePipeline = null;
        switch (sparkRepository.getDataSource().getDataSourceType()) {
            case CASSANDRA:
                writePipeline = new CassandraWritePipeline(pipelineContext, (SparkCassandraRepository) sparkRepository);
                break;
            case FILESYSTEM:
                writePipeline = new FileSystemWritePipeline(pipelineContext, (SparkFileSystemRepository) sparkRepository);
                break;
            default:
        }
        this.pipelineList.add(writePipeline);
        return this;
    }

    public void run() {
        for (Pipeline pipeline : pipelineList) {
            pipeline.process();
        }
    }
//    read cassandra keyspace tabble  mapper
//    flatmap mapper
//    readuce
//    sort (comparat)
//    write(path)

}
