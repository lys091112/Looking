package com.crescent.alert.engine.provider.event.boundingBox;

import java.time.Duration;
import lombok.Getter;

@Getter
public class TimeBoundingBox implements BoundingBox {

    private final Duration duration;

    public TimeBoundingBox(Duration duration) {
        this.duration = duration;
    }

}
