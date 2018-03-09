package com.crescent.alert.core.config;

import com.crescent.alert.common.util.ConfigPrefix;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 */
@Data
public class LookingConfig {

    private List<ConsumerConfig> consumers;

    private List<ProducerConfig> producers;

    @Data
    @ConfigPrefix("consumers")
    public static class ConsumerConfig {

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

    /**
     */
    @Data
    @ToString
    @ConfigPrefix("producers")
    public static class ProducerConfig {

        private String bootstrapServers;
        private String maxBlockMs;
        private String keySerializer;
        private String valueSerializer;
        private String topic;
        private String clientId;
        private List<String> regions;
    }
}
