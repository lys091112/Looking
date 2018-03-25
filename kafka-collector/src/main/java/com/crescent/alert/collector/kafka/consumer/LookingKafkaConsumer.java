package com.crescent.alert.collector.kafka.consumer;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.util.LookingThreadFactory;
import com.crescent.alert.core.collector.consumer.LookingConsumer;
import com.crescent.alert.core.collector.consumer.AbstractConsumer;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 需要添加自定义的serializer 来解析kafka数据
@Slf4j
public class LookingKafkaConsumer implements LookingConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookingKafkaConsumer.class);

    private List<ExecutorService> executorServices = new ArrayList<>();

    private List<AbstractConsumer> workers = new ArrayList<>();

    private static final String CONSUMER_TYPE = "kafka";

    public LookingKafkaConsumer() {
    }

    @Override
    public void init(List<ConsumerConfig> consumerConfigs, EventDispatcher dispatcher) {
        Preconditions.checkNotNull(consumerConfigs, "consumer name can't be empty");
        Preconditions.checkArgument(consumerConfigs.size() > 0, "consumer name can't be empty");

        for (ConsumerConfig config : consumerConfigs) {
            if (!CONSUMER_TYPE.equalsIgnoreCase(config.getConsumerType())) {
                continue;
            }

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
            LOGGER.warn("there's no consumer with type kafka");
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
}
