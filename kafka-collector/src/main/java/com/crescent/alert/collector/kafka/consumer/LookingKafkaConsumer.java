package com.crescent.alert.collector.kafka.consumer;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.util.LookingThreadFactory;
import com.crescent.alert.core.collector.consumer.AbstractLookingConsumer;
import com.crescent.alert.core.collector.consumer.Worker;
import com.crescent.alert.core.dispatch.EventDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LookingKafkaConsumer extends AbstractLookingConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookingKafkaConsumer.class);

    private List<ExecutorService> executorServices = new ArrayList<>();

    private List<Worker> workers = new ArrayList<>();

    public LookingKafkaConsumer() {
    }

    @Override
    public void init0(List<ConsumerConfig> consumerConfigs, EventDispatcher dispatcher) {
        for (ConsumerConfig config : consumerConfigs) {
            ExecutorService executor = Executors
                .newFixedThreadPool(config.getThreadNums(), new LookingThreadFactory("looking-" + config.getName()));
            workers.addAll(IntStream.range(0, config.getThreadNums()).mapToObj((int t) -> {
                    KafkaConsumerImpl impl = new KafkaConsumerImpl(config, dispatcher);
                    executor.submit(impl);
                    return impl;
                }
            ).collect(Collectors.toList()));
            executorServices.add(executor);
        }

        if (workers.isEmpty()) {
            LOGGER.warn("there's no consumer with type " + consumerType());
        }
    }

    @Override
    public void close() {
        workers.forEach(worker -> {
            try {
                worker.close();
            } catch (InterruptedException e) {
                log.error("close worker error! workerName:{}, message:{}", worker.getName(), e.getMessage());
            }
        });

        executorServices.forEach(executor -> executor.shutdown());
    }

    @Override
    public String consumerType() {
        return "kafka";
    }
}
