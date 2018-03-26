package com.crescent.alert.collector.kafka.producer;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.crescent.alert.collector.kafka.config.LookingKafkaProducerConfig;
import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.common.util.JsonObjectConverter;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSender<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

    private KafkaProducer<K, V> producer;

    private String topic;

    public KafkaSender(ProducerConfig producerConfig) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("config info:{}", producerConfig);
        }
        Preconditions.checkNotNull(producerConfig, "kafka producer config can't be empty");
        Preconditions.checkNotNull(producerConfig.getProperty(), "producer properties can't be null");

        LookingKafkaProducerConfig config;
        try {
            config = JsonObjectConverter.readValueWithObject(producerConfig.getProperty(), LookingKafkaProducerConfig.class);
        } catch (IOException e) {
            LOGGER.error("producer config regine is empty! region:{}", producerConfig.toString());
            throw new InitializationException("producer config regine is empty! region:" + producerConfig.getRegions());
        }

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(CLIENT_ID_CONFIG, config.getClientId());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, config.getValueSerializer());
        props.put(MAX_BLOCK_MS_CONFIG, config.getMaxBlockMs());  //超时时间

        this.producer = new KafkaProducer<K, V>(props);
        this.topic = config.getTopic();
    }

    protected void send(K key, V value) {
        producer.send(new ProducerRecord<K, V>(topic, key, value));
    }
}
