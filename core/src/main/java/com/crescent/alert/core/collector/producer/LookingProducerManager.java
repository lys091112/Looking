package com.crescent.alert.core.collector.producer;

import com.crescent.alert.common.exception.NotFountException;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookingProducerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookingProducerManager.class);

    private List<LookingProducer> producers = new ArrayList<>();

    private Map<String, List<LookingProducer>> producerMaps = new ConcurrentHashMap<>();

    public LookingProducerManager(List<LookingProducer> producers) {
        Preconditions.checkNotNull(producers, "producer configs is empty");
        Preconditions.checkArgument(producers.size() > 0, "producer configs is empty");
        this.producers = producers;
    }

    public void send(AlertEvent event) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("send info: {}", event);
        }
        List<LookingProducer> sendQueue;
        if (producerMaps.containsKey(event.getRegion())) {
            sendQueue = producerMaps.get(event.getRegion());
        } else {
            sendQueue = producers.stream().filter(producer -> producer.isValidRegion(event.getRegion()))
                .collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(sendQueue)) {
            throw new NotFountException("can't find producer to send event msg! region:" + event.getRegion());
        }
        sendQueue.forEach(sender -> sender.send(event));

    }
}
