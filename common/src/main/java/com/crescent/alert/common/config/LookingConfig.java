package com.crescent.alert.common.config;

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

    private String eventWindowType;

    @Data
    public static class ConsumerConfig {

        private String name;
        private int threadNums;
        private String type;
        private Properties property;
    }

    /**
     */
    @Data
    @ToString
    public static class ProducerConfig {

        private List<String> regions;
        private String type;
        private Properties property;
    }
}
