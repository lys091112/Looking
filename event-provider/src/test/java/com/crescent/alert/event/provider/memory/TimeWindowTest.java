package com.crescent.alert.event.provider.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TimeWindowTest {

    @Test
    public void test_window() {
        TimeWindow window = new TimeWindow(1000L, 9999L);
        assertThat(window.getStartTime()).isEqualTo(1000L);
        assertThat(window.getEndTime()).isEqualTo(9999L);
    }

    @Test
    public void test_invalid_window() {
        Throwable throwable = catchThrowable(() -> new TimeWindow(1000L, 999L));
        assertThat(throwable).hasMessage("endTime is less than startTime");

    }

}