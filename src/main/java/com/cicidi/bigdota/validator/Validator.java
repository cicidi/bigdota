package com.cicidi.bigdota.validator;

import java.io.Serializable;

public interface Validator<T> extends Serializable {
    boolean validate(T t);
}
