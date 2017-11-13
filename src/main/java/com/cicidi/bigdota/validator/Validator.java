package com.cicidi.bigdota.validator;

public interface Validator<T> {
    boolean validate(T t);
}
