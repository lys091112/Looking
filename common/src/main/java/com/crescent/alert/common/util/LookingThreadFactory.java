package com.crescent.alert.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LookingThreadFactory implements ThreadFactory {

    private String name;
    private AtomicInteger index = new AtomicInteger(0);

    public LookingThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(name + "--" + index.getAndIncrement());
        thread.setDaemon(false);
        return thread;
    }
}
