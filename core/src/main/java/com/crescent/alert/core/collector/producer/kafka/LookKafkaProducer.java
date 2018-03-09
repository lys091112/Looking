package com.crescent.alert.core.collector.producer.kafka;

import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.config.LookingConfig.ProducerConfig;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 针对不同的region，各建立一个producer
 */
@Slf4j
public class LookKafkaProducer implements LookingProducer {

    private Map<String, KafkaProducer> producers = new HashMap<>();

    private Map<String, ProducerConfig> regionConfig = new HashMap<>();

    public LookKafkaProducer(List<ProducerConfig> configs) {
        if (CollectionUtils.isEmpty(configs)) {
            throw new InitializationException("kafka producer config can't be empty");
        }

        for (ProducerConfig config : configs) {
            if (CollectionUtils.isEmpty(config.getRegions())) {
                log.warn("producer config regine is empty! region:{}", config.toString());
                continue;
            }
            Properties props = new Properties();
            props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                config.getBootstrapServers());
            props.put(org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
            props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                config.getKeySerializer());
            props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                config.getValueSerializer());
            props.put(org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG,
                config.getMaxBlockMs());  //超时时间
            KafkaProducer producer = new KafkaProducer<>(props);

            for (String region : config.getRegions()) {
                regionConfig.put(region, config);
                producers.put(region, producer);
            }
        }
    }

    @Override
    public void send(AlertEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("send Info: {}", event.toString());
        }
        if (!isValidEvent(event)) {
            log.error("can't find producer! event ruleId:{}, streamId:{}, region:{}", event.getRuleId(), event
                .getStreamId(), event.getRegion());
            return;
        }

        producers.getOrDefault(event.getRegion(), producers.get(Constants.DEFAULT_REGION))
            .send(new ProducerRecord(
                regionConfig.getOrDefault(event.getRegion(), regionConfig.get(Constants.DEFAULT_REGION)).getTopic(),
                event.getRuleId(), event));
    }

    private boolean isValidEvent(AlertEvent event) {
        if (producers.containsKey(event.getRegion()) && regionConfig.containsKey(regionConfig)) {
            return true;
        }

        if (producers.containsKey(Constants.DEFAULT_REGION) && regionConfig.containsKey(Constants.DEFAULT_REGION)) {
            return true;
        }
        return false;
    }
}
