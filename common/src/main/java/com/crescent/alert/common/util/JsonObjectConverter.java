package com.crescent.alert.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Getter;

public class JsonObjectConverter {

    private JsonObjectConverter() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JsonObjectConverter instance = new JsonObjectConverter();


    public static <T> T readValue(String content, Class<T> valueType) throws IOException {
        return instance.getMapper().readValue(content, valueType);
    }

    public static ObjectMapper configure(DeserializationFeature f, boolean state) {
        return instance.getMapper().configure(f, state);
    }

    /**
     * 将java properties 对象转为其他对象数据
     */
    public static <T> T readValueWithObject(Object source, Class<T> valueType) throws IOException {
        String targetStr = instance.getMapper().writeValueAsString(source);
        return readValue(targetStr, valueType);
    }

    @Getter
    private ObjectMapper mapper = null;

}
