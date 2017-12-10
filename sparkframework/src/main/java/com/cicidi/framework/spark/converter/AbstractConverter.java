package com.cicidi.framework.spark.converter;

import com.cicidi.utilities.JSONUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractConverter<IN> implements Serializable {

    protected List<AbstractConvertStrategy> convertStrategyList;

    public AbstractConverter() {
    }

    public abstract IN format(IN input);

    protected void validate() {
        if (CollectionUtils.isEmpty(convertStrategyList)) {
            throw new BadRequestException("DataConverter strategy can not be null");
        }
    }

    public Map extractToMap(IN input) {
        IN formatted = format(input);
        this.validate();
        Map output = new ConcurrentHashMap();

        for (AbstractConvertStrategy convertStrategy : convertStrategyList) {
            convertStrategy.start(formatted, null, output);
        }
        return output;
    }

    public String extract(IN input) {
        try {
            String str = JSONUtil.getObjectMapper().writeValueAsString(this.extractToMap(input));
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addStrategy(AbstractConvertStrategy convertStrategy) {
        if (convertStrategyList == null) {
            convertStrategyList = new ArrayList<>();
        }
        this.convertStrategyList.add(convertStrategy);
    }
}
