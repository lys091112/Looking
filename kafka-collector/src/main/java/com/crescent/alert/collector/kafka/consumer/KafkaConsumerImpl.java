package com.crescent.alert.collector.kafka.consumer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import com.crescent.alert.collector.kafka.config.LookingKafkaConsumerConfig;
import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.common.util.JsonObjectConverter;
import com.crescent.alert.core.collector.consumer.Worker;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerImpl extends Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    KafkaConsumer<String, AlertEvent> consumer;

    private LookingKafkaConsumerConfig config;

    public KafkaConsumerImpl(ConsumerConfig consumerConfig, EventDispatcher dispatcher) {
        super(consumerConfig.getName(), dispatcher);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("consumer info:{}", consumerConfig);
        }

        String name = consumerConfig.getName();
        Preconditions.checkNotNull(name, "consumer name can't be empty");
        Preconditions.checkNotNull(name, "consumer info can't be null");
        Preconditions.checkNotNull(consumerConfig.getProperty(), "consumer properties can't be null");

        LookingKafkaConsumerConfig config;
        try {
            config = JsonObjectConverter.readValueWithObject(consumerConfig.getProperty(), LookingKafkaConsumerConfig.class);
        } catch (IOException e) {
            LOGGER.error("convert to LookingKafkaConsumerConfig from json error! consumer name is {}, error:{}", name, e);
            throw new InitializationException("convert to LookingKafkaConsumerConfig from json error! consumer name=" + name);
        }

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(GROUP_ID_CONFIG, config.getGroupId());
        props.put(ENABLE_AUTO_COMMIT_CONFIG, config.getAutoCommit());
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, config.getAutoCommitIntervalMs());
        props.put(SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeoutMs());
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueSerializer());
        props.put(AUTO_OFFSET_RESET_CONFIG, "latest");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(config.getTopic()));
        this.config = config;
    }

    @Override
    public void doWork() {
        ConsumerRecords<String, AlertEvent> records = consumer.poll(1000);
        for (ConsumerRecord<String, AlertEvent> record : records) {
            dispatcher.dispatcher(record.value());
        }
    }
}
