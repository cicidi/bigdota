package com.cicidi.bigdota.converter.dota;

import com.cicidi.framework.spark.converter.AbstractConverter;

public class DotaConverter extends AbstractConverter<String> {

    public DotaConverter() {
    }

    @Override
    protected void validate() {
        super.validate();
    }

    @Override
    public String format(String input) {
        return input;
    }

}
