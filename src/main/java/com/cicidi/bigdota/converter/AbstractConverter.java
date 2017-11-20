package com.cicidi.bigdota.converter;

import com.cicidi.utilities.JSONUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractConverter<IN> {
    protected IN input;

    protected List<AbstractConvertStrategy> convertStrategyList;

    protected Map output = new ConcurrentHashMap();

    protected abstract void init();

    protected abstract void validate();

    protected ObjectMapper objectMapper = JSONUtil.getObjectMapper();

    public AbstractConverter(IN input) {
        this.input = input;
        init();
        validate();
    }

    public Map process() {
        for (AbstractConvertStrategy convertStrategy : convertStrategyList) {
            convertStrategy.start(this.input, null, output);
        }
        return this.output;
    }

    public void addStrategy(AbstractConvertStrategy convertStrategy) {

        if (convertStrategyList == null) {
            convertStrategyList = new ArrayList<>();
        }
        this.convertStrategyList.add(convertStrategy);
    }

    public String getFilteredData() {
        process();
        try {
            if (this.output.size() > 0) {
                String str = objectMapper.writeValueAsString(this.output);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
