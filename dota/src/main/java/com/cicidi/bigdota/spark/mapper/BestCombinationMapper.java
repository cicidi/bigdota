package com.cicidi.bigdota.spark.mapper;

import com.cicidi.framework.spark.analyze.Accumulatable;
import com.cicidi.framework.spark.mapper.Mapper;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.springframework.stereotype.Component;
import scala.Tuple2;

@Component
public class BestCombinationMapper extends Mapper<Tuple2<String, Integer>, Row> implements Accumulatable {


    public BestCombinationMapper(SparkContext sparkContext) {
        super();
        this.accumulatorV2 = this.createAccumulator(sparkContext, "BEST_COMBINATION", Accumulatable.LONG);
    }

    @Override
    public Row call(Tuple2<String, Integer> t) throws Exception {
        this.accumulate(1L);
        return RowFactory.create(t._1, t._2);
    }
}
