package com.crescent.alert.core.dispatch.provider.memory;

import static java.util.stream.StreamSupport.stream;

import com.crescent.alert.core.dispatch.provider.BaseEventsProvider;
import com.crescent.alert.engine.provider.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * TODO {crescent} 数据遍历需要优化，尽可能高效的查询
 * shit code
 */
public class EventsProvider extends BaseEventsProvider {

    private int rate = 3;

    private ConcurrentHashMap<String, ArrayList<Event>> events = new ConcurrentHashMap<>();

    @Override
    public List<Event> findEventsByFixedCount(String streamId, int count) {
        if (!events.containsKey(streamId)) {
            return Collections.emptyList();
        }

        ArrayList<Event> lst = events.get(streamId);
        checkAndCleanCount(streamId, lst);
        List<Event> res = new ArrayList<>(count);
        for (int i = lst.size() - 1; count > 0; i--, count--) {
            res.add(lst.get(i));
        }
        return res;
    }

    private void checkAndCleanCount(String streamId, ArrayList<Event> lst) {
        int bufferLength = bufferSize(streamId);
        if (lst.size() > bufferLength * rate) {
            lst.subList(0, lst.size() - bufferLength - 1).clear();
        }
    }


    @Override
    public List<Event> findEventsByTimeWindow(String streamId, long startTime, long endTime) {
        if (!events.containsKey(streamId)) {
            return Collections.emptyList();
        }

        ArrayList<Event> lst = events.get(streamId);
        checkAndCleanWindow(streamId, lst);
        return stream(lst.spliterator(), false)
            .filter(event -> startTime < event.getTimestamp() && event.getTimestamp() <= endTime)
            .collect(Collectors.toList());
    }

    /**
     * 清理过期的buffer数据, 5分钟清理一次
     */
    private void checkAndCleanWindow(String streamId, ArrayList<Event> lst) {
        int bufferLength = bufferSize(streamId); // 规则buffer保留的毫秒数
        long currentTime = System.currentTimeMillis();
        if (currentTime - lst.get(0).getTimestamp() > bufferLength + TimeUnit.MINUTES.toMillis(5)) {
            int index = 0;
            for (int i = 0; i < lst.size(); i++) {
                if (lst.get(i).getTimestamp() >= currentTime - bufferLength) {
                    index = i - 1;
                    break;
                }
            }
            if (index > 0) {
                lst.subList(0, index).clear();
            }
        }
    }

    @Override
    public void clear(Event event) {
        if (events.containsKey(event.getStreamId())) {
            events.get(event.getStreamId()).clear();
            events.get(event.getStreamId()).add(event);
        }
    }

    @Override
    public boolean addEvent(Event event) {
        if (!events.containsKey(event.getRuleId())) {
            events.put(event.getRuleId(), new ArrayList<>());
        }
        return events.get(event.getRuleId()).add(event);
    }
}
