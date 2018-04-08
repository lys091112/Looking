package com.crescent.alert.event.provider.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentHashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

@RunWith(BlockJUnit4ClassRunner.class)
public class MemoryEventsProviderTest {

    ConcurrentHashMap<String, EventCycle> events = Mockito.mock(ConcurrentHashMap.class);
    MemoryEventsProvider provider;

    @Before
    public void setUp() {
        this.provider = new MemoryEventsProvider();
        provider.setEvents(events);
    }


    @Test
    public void clear_contain_streamId() throws Exception {
        when(events.containsKey("streamId")).thenReturn(false);
        provider.clear("streamId");
        verify(events, times(1)).containsKey("streamId");
        verify(events, times(0)).get("streamId");
    }

    @Test
    public void clear() {
        EventCycle cycle = Mockito.mock(EventCycle.class);
        when(events.containsKey("streamId")).thenReturn(true);
        when(events.get("streamId")).thenReturn(cycle);
        doNothing().when(cycle).clear();
        provider.clear("streamId");

        verify(events, times(1)).containsKey("streamId");
        verify(events, times(1)).get("streamId");
        verify(cycle, times(1)).clear();
    }

    @Test
    public void test_event_window_type() throws Exception {
        assertThat(provider.eventWindowType()).isEqualTo("memory");
    }

    @Test
    public void doFindEventsByFixedCount() throws Exception {
    }

    @Test
    public void doFindEventsByTimeWindow() throws Exception {
    }

    @Test
    public void sink() throws Exception {
    }

}