package com.crescent.alert.core.collector.consumer.kafka;

import com.crescent.alert.core.collector.consumer.Worker;
import com.crescent.alert.core.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

// 需要添加自定义的serializer 来解析kafka数据
public class KafkaWorker extends Worker {

    KafkaConsumer<String, AlertEvent> consumer;

    private ConsumerConfig config;

    public KafkaWorker(ConsumerConfig config, EventDispatcher dispatcher) {
        super(config.getName(), dispatcher);
        Properties props = new Properties();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.getAutoCommit());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.getAutoCommitIntervalMs());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeoutMs());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueSerializer());
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

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
