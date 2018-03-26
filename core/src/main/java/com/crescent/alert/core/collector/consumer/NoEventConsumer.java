package com.crescent.alert.core.collector.consumer;

import com.crescent.alert.core.dispatch.EventDispatcher;

/**
 * TODO 无数据告警处理worker
 */
public class NoEventConsumer extends Worker {

    public NoEventConsumer(EventDispatcher dispatcher) {
        super("no-event", dispatcher);
    }

    @Override
    public void doWork() {

    }
}
