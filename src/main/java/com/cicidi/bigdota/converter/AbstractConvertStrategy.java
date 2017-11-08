package com.cicidi.bigdota.converter;

import java.util.Map;

public abstract class AbstractConvertStrategy<IN, OUT, PREDECESSOR_OUTPUT, FIELD> {
    protected FIELD fieldName;

    protected AbstractConvertStrategy[] successors;

    protected abstract boolean isEnabled();

    public Map start(IN m, PREDECESSOR_OUTPUT predecessorOutput, Map map) {
        OUT out = process(m, predecessorOutput);
        map.put(this.fieldName, out);
        for (AbstractConvertStrategy successor : successors) {
            if (successor != null)
                successor.start(m, out, map);
        }
        return map;
    }

    protected abstract OUT process(IN m, PREDECESSOR_OUTPUT predecessorOutput);

    public AbstractConvertStrategy(FIELD fieldName, AbstractConvertStrategy... successors) {
        this.fieldName = fieldName;
        this.successors = successors;

    }
}
