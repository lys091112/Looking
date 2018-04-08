package com.crescent.alert.event.provider.memory;

import static org.assertj.core.api.Assertions.assertThat;

import com.crescent.alert.engine.provider.Event;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class EventCycleTest {

    @Test
    public void get_events_by_count() {
        EventCycle eventCycle = new EventCycle(3);
        List<Event> events = EventsBuilder.buildEvents(5);

        events.forEach(event -> eventCycle.sink(event));

        List<Event> res = eventCycle.getEventsByCount(5);

        assertThat(eventCycle.getCurrentIdx()).isEqualTo(2);
        assertThat(res.size()).isEqualTo(3);
        Event currentNode = res.get(0);
        assertThat(currentNode.getStreamId()).isEqualTo("streamId4");
    }

    @Test
    public void get_events_by_window() {
        EventCycle eventCycle = new EventCycle(6);

        List<Event> events = EventsBuilder.buildEvents(10);

        events.forEach(event -> eventCycle.sink(event));

        List<Event> res = eventCycle.getEventsByWindow(new TimeWindow(System.currentTimeMillis() - TimeUnit.MINUTES
            .toMillis(3), System.currentTimeMillis()));

        assertThat(eventCycle.getCurrentIdx()).isEqualTo(4);
        assertThat(res.size()).isEqualTo(3);
        Event previousCurrentNode = res.get(1);
        assertThat(previousCurrentNode.getStreamId()).isEqualTo("streamId8");
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_event_by_count_with_illegal_count() {
        EventCycle eventCycle = new EventCycle(3);
        List<Event> events = EventsBuilder.buildEvents(5);

        events.forEach(event -> eventCycle.sink(event));

        eventCycle.getEventsByCount(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_event_by_count_with_illegal_window() {
        EventCycle eventCycle = new EventCycle(3);
        List<Event> events = EventsBuilder.buildEvents(5);

        events.forEach(event -> eventCycle.sink(event));

        eventCycle.getEventsByWindow(new TimeWindow(1000L, 999L));
    }


}