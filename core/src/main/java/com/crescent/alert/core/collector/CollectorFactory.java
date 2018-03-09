package com.crescent.alert.core.collector;

import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.collector.producer.kafka.LookKafkaProducer;
import com.crescent.alert.core.config.LookingConfig.ProducerConfig;
import java.util.List;

public class CollectorFactory {

    public static LookingProducer createProducer(List<ProducerConfig> producers) {
            return new LookKafkaProducer(producers);
    }

}
