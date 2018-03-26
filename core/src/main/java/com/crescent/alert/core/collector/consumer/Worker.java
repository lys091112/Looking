package com.crescent.alert.core.collector.consumer;

import com.crescent.alert.core.dispatch.EventDispatcher;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Worker implements Runnable {

    @Getter
    private String name;

    private AtomicBoolean running = new AtomicBoolean(true);

    private CountDownLatch shutDown = new CountDownLatch(1);

    protected EventDispatcher dispatcher;

    public Worker(String name, EventDispatcher dispatcher) {
        this.name = name;
        this.dispatcher = dispatcher;
    }

    public void close() throws InterruptedException {
        if (running.compareAndSet(true, false)) {
            log.info("close consumer! name=" + name);
        }
        shutDown.wait();
    }

    public abstract void doWork();

    @Override
    public void run() {
        log.info("started! name:{}", name);
        try {
            while (running.get()) {
                doWork();
            }
        } catch (Exception e) {
            running.set(false);
            shutDown.countDown();
            log.info("consumer stopped. name:{}, exception:{}", name, e.getMessage());
            return;
        }
        shutDown.countDown();
        log.info("success stopped! name:{}", name);
    }

}
