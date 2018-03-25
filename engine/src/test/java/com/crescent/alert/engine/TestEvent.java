package com.crescent.alert.engine;

import com.crescent.alert.engine.provider.Event;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;

@Data
public class TestEvent implements Event, Comparable<Event> {

    private String key;
    private String ruleId;
    private String eventCategory;
    private Map<String, String> metrics;
    private String severity;
    private long timestamp;

    @Override
    public int compareTo(@NonNull Event o) {
        return (int) (o.getTimestamp() - this.timestamp);
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public String getStreamId() {
        return null;
    }

    @Override
    public Map<String, String> getMetrics() {
        return metrics;
    }

    @Override
    public String getRuleId() {
        return null;
    }

    public TestEvent(String key, Map<String, String> metrics, long timestamp) {
        this.key = key;
        this.metrics = metrics;
        this.timestamp = timestamp;
    }
}
