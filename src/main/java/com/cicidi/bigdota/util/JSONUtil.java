package com.cicidi.bigdota.util;

import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.GameModeEnum;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;

import static org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES;

//import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class JSONUtil {

    static final ObjectMapper OBJECT_MAPPER = initializeObjectMapper();

    static final ObjectMapper OBJECT_MAPPER_WITH_WRAP_ROOT_VALUE = initializeObjectMapper_WITH_WRAP_ROOT_VALUE();

    public static final ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper initializeObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public static ObjectMapper initializeObjectMapper_WITH_WRAP_ROOT_VALUE() {
        return initializeObjectMapper().configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
    }

    public static String convertToString(Object obj)
            throws JsonGenerationException, JsonMappingException, IOException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    public static String convertToString_WITH_WRAP_ROOT_VALUE(Object obj)
            throws JsonGenerationException, JsonMappingException, IOException {
        return OBJECT_MAPPER_WITH_WRAP_ROOT_VALUE.writeValueAsString(obj);
    }
}