package com.crescent.alert.core.collector.producer;

import com.crescent.alert.common.config.LookingConfig.ProducerConfig;
import java.util.List;

public interface LookingProducer extends Sender {

    public void init(List<ProducerConfig> configs);

    public boolean isValidRegion(String region);

    public String getProducerType();

}
