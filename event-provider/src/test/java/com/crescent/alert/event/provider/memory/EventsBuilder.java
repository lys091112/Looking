package com.crescent.alert.event.provider.memory;

import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.engine.provider.Event;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventsBuilder {

    /**
     * private String ruleId;
     * private String streamId;
     * private long timestamp;
     * private Map<String, String> metrics;
     * private String severityLevel;
     * private EventCategory eventCategory;
     * private String region;
     * private long ttl;
     */

    public static List<Event> buildEvents(int count) {
        return buildEvents(count, TimeUnit.MINUTES);
    }

    public static List<Event> buildEvents(int count, TimeUnit period) {
        long startTime = System.currentTimeMillis() - period.toMillis(count - 1);

        return IntStream.range(0, count)
            .mapToObj(i -> new AlertEvent().setStreamId("streamId" + i).setTimestamp(startTime + period.toMillis(i))
                .setRegion("test")).collect(Collectors.toList());
    }

}
