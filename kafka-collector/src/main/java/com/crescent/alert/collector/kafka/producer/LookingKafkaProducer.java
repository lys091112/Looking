package com.crescent.alert.collector.kafka.producer;

import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import com.crescent.alert.core.collector.producer.AbstractLookingProducer;
import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.base.Preconditions;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 针对不同的region，各建立一个producer
 */
public class LookingKafkaProducer extends AbstractLookingProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookingProducer.class);

    private Map<String, KafkaSenderImpl> producers = new ConcurrentHashMap<>();

    private Set<String> regions = new HashSet<>();

    public LookingKafkaProducer() {
    }

    @Override
    public void init0(List<ProducerConfig> configs) {
        Preconditions.checkNotNull(configs, "kafka producer config can't be empty");

        for (ProducerConfig config : configs) {

            KafkaSenderImpl<String, AlertEvent> producer = new KafkaSenderImpl(config);

            for (String region : config.getRegions()) {
                producers.putIfAbsent(region, producer);
            }
            this.regions.addAll(config.getRegions());
        }

    }

    @Override
    public void send(AlertEvent event) {
        if (!producers.containsKey(event.getRegion())) {
            LOGGER.error("can't find producer by region {}", event.getRegion());
        }
        producers.get(event.getRegion()).send(event.getRuleId(), event);
    }

    @Override
    public boolean isValidRegion(String region) {
        return regions.contains(region);
    }

    @Override
    public String getProducerType() {
        return "kafka";
    }
}
