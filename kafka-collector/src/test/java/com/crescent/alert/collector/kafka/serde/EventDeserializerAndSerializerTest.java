package com.crescent.alert.collector.kafka.serde;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class EventDeserializerAndSerializerTest {

    @Test
    public void event_deserilizer() {
        AlertEvent event = buildEvent();
        EventSerializer serializer = new EventSerializer();
        byte[] datas = serializer.serialize("test", event);

        EventDeserializer deserializer = new EventDeserializer();
        AlertEvent deserializerEvent = deserializer.deserialize("test", datas);

        assertThat(event.getStreamId(), equalTo(deserializerEvent.getStreamId()));
        assertThat(event.getRegion(), equalTo(deserializerEvent.getRegion()));
        assertThat(event.getTimestamp(), equalTo(deserializerEvent.getTimestamp()));
        assertThat(event.getEventCategory(), equalTo(deserializerEvent.getEventCategory()));
        assertThat(event.getTtl(), equalTo(deserializerEvent.getTtl()));
        assertThat(event.getMetrics(), equalTo(deserializerEvent.getMetrics()));
    }

    public AlertEvent buildEvent() {
        return new AlertEvent().setStreamId("streamId").setRegion("alert")
            .setEventCategory(EventCategory.RawMetric)
            .setTimestamp(System.currentTimeMillis())
            .setMetrics(ImmutableMap.of("AVG", "25"))
            .setTtl(18000L);
    }

}