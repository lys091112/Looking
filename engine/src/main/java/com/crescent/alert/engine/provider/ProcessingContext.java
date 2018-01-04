package com.crescent.alert.engine.provider;

import com.crescent.alert.engine.Event;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import lombok.Data;

@Data
public class ProcessingContext {

    private Map<AbstractAggregationOperand, Future<Double>> aggregationValues;
    private Event currentEvent;

    // 用于告警计算的历史数据
    private List<Event> previousEvents;

    // 告警阈值，触发告警的门限值
    private Map<String,String> threshold;

    public ProcessingContext(Event currentEvent, Map<String,String> threshold) {
        this(currentEvent,Collections.emptyList(),threshold);
    }

    public ProcessingContext(Event currentEvent,List<Event> previousEvents, Map<String,String> threshold) {
        this.currentEvent = currentEvent;
        this.previousEvents = Optional.ofNullable(previousEvents).orElse(Collections.emptyList());
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return currentEvent.toString();
    }
}
