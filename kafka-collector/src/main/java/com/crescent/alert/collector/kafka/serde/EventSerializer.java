package com.crescent.alert.collector.kafka.serde;

import com.crescent.alert.common.exception.ErrorCode;
import com.crescent.alert.common.exception.UserSerializetaionException;
import com.crescent.alert.core.domain.AlertEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 */
public class EventSerializer implements Serializer<AlertEvent> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, AlertEvent data) {
        try {
            if (null == data) {
                return null;
            } else {
                return objectMapper.writeValueAsBytes(data);
            }
        } catch (JsonProcessingException e) {
            throw new UserSerializetaionException(ErrorCode.SerializeException,
                "Error when serializing AlertEvent to byte");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
