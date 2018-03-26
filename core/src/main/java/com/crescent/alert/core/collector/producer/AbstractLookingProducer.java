package com.crescent.alert.core.collector.producer;

import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractLookingProducer implements LookingProducer {

    public void init(List<ProducerConfig> configs) {
        init0(configs.stream().filter(config -> getProducerType().equalsIgnoreCase(config.getType()))
            .collect(Collectors.toList()));

    }

    public abstract void init0(List<ProducerConfig> configs);


}
