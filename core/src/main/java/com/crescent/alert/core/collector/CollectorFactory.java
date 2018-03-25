package com.crescent.alert.core.collector;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.core.collector.consumer.LookingConsumer;
import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectorFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorFactory.class);

    public static List<LookingProducer> createProducers(List<ProducerConfig> configs) {
        Preconditions.checkNotNull(configs, "producer configs is empty");
        Preconditions.checkArgument(configs.size() > 0, "producer configs is empty");

        List<LookingProducer> res = new ArrayList<>();
        ServiceLoader<LookingProducer> loadProducers = ServiceLoader.load(LookingProducer.class);
        Iterator<LookingProducer> iterator = loadProducers.iterator();
        while (iterator.hasNext()) {
            LookingProducer producer = iterator.next();
            producer.init(configs);
            res.add(producer);
        }
        if (res.isEmpty()) {
            LOGGER.error("No LookingProducer Impl can be found with ServiceLoader, please check your dependencies");
            throw new InitializationException(
                "No LookingProducer Impl can be found with ServiceLoader, please check your dependencies");
        }
        return res;
    }


    public static List<LookingConsumer> createConsumers(List<ConsumerConfig> configs, EventDispatcher dispatcher) {
        Preconditions.checkNotNull(configs, "producer configs is empty");
        Preconditions.checkArgument(configs.size() > 0, "producer configs is empty");

        List<LookingConsumer> res = new ArrayList<>();
        ServiceLoader<LookingConsumer> consumers = ServiceLoader.load(LookingConsumer.class);
        Iterator<LookingConsumer> iterator = consumers.iterator();
        while (iterator.hasNext()) {
            LookingConsumer consumer = iterator.next();
            consumer.init(configs, dispatcher);
            res.add(consumer);
        }
        if (res.isEmpty()) {
            LOGGER.error("No LookingConsumer Impl is initialed by ServiceLoader, please check your dependencies and properties");
            throw new InitializationException(
                "No LookingConsumer Impl is initialed by ServiceLoader, please check your dependencies and properties");
        }
        return res;
    }

}
