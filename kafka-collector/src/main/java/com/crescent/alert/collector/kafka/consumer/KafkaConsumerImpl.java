package com.crescent.alert.collector.kafka.consumer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.core.collector.consumer.Worker;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerImpl extends Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    KafkaConsumer<String, AlertEvent> consumer;

    public KafkaConsumerImpl(ConsumerConfig consumerConfig, EventDispatcher dispatcher) {
        super(consumerConfig.getName(), dispatcher);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Consumer info:{}", consumerConfig);
        }

        String name = consumerConfig.getName();
        Preconditions.checkNotNull(name, "Consumer name can't be empty");
        Preconditions.checkNotNull(name, "Consumer info can't be null");
        Preconditions.checkNotNull(consumerConfig.getProperty(), "Consumer properties can't be null");

        String t = consumerConfig.getProperty().getProperty("topics");
        if (StringUtils.isBlank(t)) {
            throw new InitializationException("The topic property can not be found from the configuration file! consumer name=" +
                name);
        }
        List<String> topics = Arrays.asList(t.split(","));

        Properties props = consumerConfig.getProperty();
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "com.crescent.alert.collector.kafka.serde.EventDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "com.crescent.alert.collector.kafka.serde.EventDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topics);
    }

    @Override
    public void doWork() {
        ConsumerRecords<String, AlertEvent> records = consumer.poll(1000);
        for (ConsumerRecord<String, AlertEvent> record : records) {
            dispatcher.dispatcher(record.value());
        }
    }
}
