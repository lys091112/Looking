package com.crescent.alert.collector.kafka.producer;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import com.crescent.alert.common.exception.InitializationException;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSenderImpl<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderImpl.class);

    private KafkaProducer<K, V> producer;

    private List<String> topics;

    public KafkaSenderImpl(ProducerConfig producerConfig) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("config info:{}", producerConfig);
        }
        Preconditions.checkNotNull(producerConfig, "kafka producer config can't be empty");
        Preconditions.checkNotNull(producerConfig.getProperty(), "producer properties can't be null");

        Properties props = producerConfig.getProperty();

        String t = props.getProperty("topics");
        if (StringUtils.isBlank(t)) {
            throw new InitializationException("The topics property can not be found from the configuration file! content =" +
                producerConfig.toString());
        }
        this.topics = Arrays.asList(t.split(","));

        props.put(KEY_SERIALIZER_CLASS_CONFIG, "com.crescent.alert.collector.kafka.serde.EventDeserializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "com.crescent.alert.collector.kafka.serde.EventDeserializer");

        this.producer = new KafkaProducer<>(props);
    }

    protected void send(K key, V value) {
        for (String topic : topics) {
            producer.send(new ProducerRecord<>(topic, key, value));
        }
    }
}
