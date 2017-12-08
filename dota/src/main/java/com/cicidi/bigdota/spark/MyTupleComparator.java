package com.cicidi.bigdota.spark;

import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;

public class MyTupleComparator implements Comparator<Tuple2<String, Integer>>, Serializable {
    final static MyTupleComparator INSTANCE = new MyTupleComparator();

    public int compare(Tuple2<String, Integer> t1,
                       Tuple2<String, Integer> t2) {
        return -t1._2.compareTo(t2._2);    // sort descending
    }
}