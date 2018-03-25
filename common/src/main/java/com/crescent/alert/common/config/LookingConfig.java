package com.crescent.alert.common.config;

import com.crescent.alert.common.util.ConfigPrefix;
import java.util.List;
import java.util.Properties;
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
        private int threadNums;
        private String consumerType;
        private Properties infos;
    }

    /**
     */
    @Data
    @ToString
    @ConfigPrefix("producers")
    public static class ProducerConfig {

        private List<String> regions;
        private String producerType;
        private Properties infos;
    }
}
