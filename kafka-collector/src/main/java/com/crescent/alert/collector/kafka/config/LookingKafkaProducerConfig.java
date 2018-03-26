package com.crescent.alert.collector.kafka.config;

import com.crescent.alert.common.util.ConfigPrefix;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ConfigPrefix("producers")
public class LookingKafkaProducerConfig {

    private String bootstrapServers;
    private String maxBlockMs;
    private String keySerializer;
    private String valueSerializer;
    private String topic;
    private String clientId;
}