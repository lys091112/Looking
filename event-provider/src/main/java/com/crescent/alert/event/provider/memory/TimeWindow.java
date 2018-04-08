package com.crescent.alert.event.provider.memory;

import com.google.common.base.Preconditions;

public class TimeWindow {

    private long startTime;

    private long endTime;

    public TimeWindow(long startTime, long endTime) {
        Preconditions.checkArgument(endTime >= startTime, "endTime is less than startTime");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
