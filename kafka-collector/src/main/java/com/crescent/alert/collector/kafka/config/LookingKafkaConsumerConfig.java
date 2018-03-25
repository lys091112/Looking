package com.crescent.alert.collector.kafka.config;

import com.crescent.alert.common.util.ConfigPrefix;
import lombok.Data;

@Data
@ConfigPrefix("consumers")
public class LookingKafkaConsumerConfig {

    private String name;
    private String bootstrapServers;
    private String groupId;
    private String autoCommit;
    private String autoCommitIntervalMs;
    private String sessionTimeoutMs;
    private String keySerializer;
    private String valueSerializer;
    private String topic;
    private int threadNums;
}