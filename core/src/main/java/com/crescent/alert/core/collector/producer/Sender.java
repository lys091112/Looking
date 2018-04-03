package com.crescent.alert.core.collector.producer;

import com.crescent.alert.core.rule.AlertEvent;

public interface Sender {

    public void send(AlertEvent event);
}
