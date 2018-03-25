package com.crescent.alert.core.collector.consumer;

import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.core.dispatch.EventDispatcher;
import java.util.List;

/**
 */
public interface LookingConsumer {

    public void close();

    public void init(List<ConsumerConfig> config, EventDispatcher dispatcher);
}
