package com.crescent.alert.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Getter;

public class JsonObjectConvertor {

    private JsonObjectConvertor() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JsonObjectConvertor instance = new JsonObjectConvertor();


    public static <T> T readValue(String content, Class<T> valueType) throws IOException {
        return instance.getMapper().readValue(content, valueType);
    }

    public static ObjectMapper configure(DeserializationFeature f, boolean state) {
        return instance.getMapper().configure(f, state);

    }

    @Getter
    private ObjectMapper mapper = null;

}
