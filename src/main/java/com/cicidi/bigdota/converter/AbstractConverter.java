package com.cicidi.bigdota.converter;

import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractConverter<IN> {
    protected IN input;

    protected List<AbstractConvertStrategy> convertStrategyList;

    protected Map map = new ConcurrentHashMap();

    protected abstract void init();

    protected abstract void validate();

    public AbstractConverter(IN input) {
        this.input = input;
        init();
        validate();
    }

    public Map process() {
        for (AbstractConvertStrategy convertStrategy : convertStrategyList) {
            convertStrategy.start(this.input, null, map);
        }
        return this.map;
    }

    public void addStrategy(AbstractConvertStrategy convertStrategy) {

        if (convertStrategyList == null) {
            convertStrategyList = new ArrayList<>();
        }
        this.convertStrategyList.add(convertStrategy);
    }

}
