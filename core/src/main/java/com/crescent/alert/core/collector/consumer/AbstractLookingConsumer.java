package com.crescent.alert.core.collector.consumer;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractLookingConsumer implements LookingConsumer {

    public void init(List<ConsumerConfig> configs, EventDispatcher dispatcher) {
        Preconditions.checkNotNull(configs, "consumer name can't be empty");
        Preconditions.checkArgument(configs.size() > 0, "consumer name can't be empty");
        init0(configs.stream().filter(config -> consumerType().equalsIgnoreCase(config.getType()))
            .collect(Collectors.toList()), dispatcher);
    }

    public abstract void init0(List<ConsumerConfig> config, EventDispatcher dispatcher);
}
