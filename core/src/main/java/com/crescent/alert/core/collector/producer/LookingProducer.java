package com.crescent.alert.core.collector.producer;

import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.List;

public interface LookingProducer{

    public void init(List<ProducerConfig> configs);

    public void send(AlertEvent event);

    public boolean isValidRegion(String region);

    public String getProducerType();

}
