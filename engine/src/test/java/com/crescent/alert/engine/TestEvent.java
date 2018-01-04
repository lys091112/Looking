package com.crescent.alert.engine;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.NonNull;

@Data
public class TestEvent implements Event, Comparable<Event> {

    private String key;
    private String ruleId;
    private String eventCategory;
    private Map<String, String> metrics;
    private String severity;
    private String id;
    private Long cycleId = 0L; // The initialize timestamp of the beginning of event cycle
    private long timestamp;
    private long timeScope;
    private long time;

    private Map<String, String> tags;

    private Long index;

    @Override
    public int compareTo(@NonNull Event o) {
        return (int) (o.getTimestamp() - this.timestamp);
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public Map<String, String> getMetrics() {
        return metrics;
    }

    @Override
    public String getStreamId() {
        return null;
    }

    @Override
    public Optional<String> readValue(String fieldName) {
        return null;
    }

    public TestEvent(String key, Map<String, String> metrics, UUID uuid, long timestamp, long timeScope, long time) {
        this.key = key;
        this.metrics = metrics;
        this.id = uuid.toString();
        this.timestamp = timestamp;
        this.timeScope = timeScope;
        this.time = time;
    }
}
