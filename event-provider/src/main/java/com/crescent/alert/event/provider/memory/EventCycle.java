package com.crescent.alert.event.provider.memory;

import com.crescent.alert.engine.provider.Event;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * 不考虑线程安全问题，同一个streamId，保证每次只会有一个线程访问
 */
public class EventCycle {

    private Event[] events;

    private int size;

    private int currentNode = 0;

    public EventCycle(int size) {
        this.size = size;
        this.events = new Event[size];
    }

    public void sink(@Nullable Event event) {
        Preconditions.checkNotNull(event, "Event can't be null");
        events[currentNode] = event;
        currentNode = getNextIdx(currentNode);
    }

    public List<Event> getEventsByWindow(TimeWindow window) {
        Preconditions.checkNotNull(window, "Window can't be null");
        Preconditions.checkArgument(window.getStartTime() < window.getEndTime(), "window endTime should be greater than "
            + "startTime");
        return getEvents((res, event) -> event.getTimestamp() >= window.getStartTime());
    }

    public List<Event> getEventsByCount(int count) {
        Preconditions.checkArgument(count > 0, "Count is less than zero!");
        return getEvents((res, event) -> res.size() < count);
    }

    private List<Event> getEvents(BiPredicate<List<Event>, Event> predicate) {
        List<Event> res = new ArrayList<>();
        int temp = getPreviousIdx(currentNode);
        int start = temp;
        while (events[temp] != null && predicate.test(res, events[temp])) {
            res.add(events[temp]);
            temp = getPreviousIdx(temp);
            // 又回到了获取的起始点，说明数据已经循环了一周，直接停止
            if (temp == start) {
                break;
            }
        }

        return res;
    }

    public void clear() {
        this.events = new Event[size];
        currentNode = 0;
    }

    private int getPreviousIdx(int index) {
        return (index - 1 + this.size) % this.size;
    }

    private int getNextIdx(int index) {
        return (index + 1 + this.size) % this.size;
    }

    protected int getCurrentIdx() {
        return currentNode;
    }
}
