package com.crescent.alert.core.collector.producer;

import com.crescent.alert.core.domain.AlertEvent;

/**
 */
public interface LookingProducer {

    public void send(AlertEvent event);

}
