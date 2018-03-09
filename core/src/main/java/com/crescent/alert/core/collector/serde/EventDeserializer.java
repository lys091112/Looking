package com.crescent.alert.core.collector.serde;

import com.crescent.alert.common.exception.ErrorCode;
import com.crescent.alert.common.exception.UserDeserializetaionException;
import com.crescent.alert.core.domain.AlertEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class EventDeserializer implements Deserializer<AlertEvent> {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public AlertEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            } else {
                return objectMapper.readValue(data, AlertEvent.class);
            }
        } catch (Exception e) {
            throw new UserDeserializetaionException(ErrorCode.DeserializeException,
                "Error when deserializing byte[] to AlertEvent ");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
