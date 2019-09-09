package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class QueueProcessor {
    private Map<String, AtomicInteger> queueStorage = new ConcurrentHashMap<>();
    protected volatile boolean running = false;

    public boolean producer(Target target, ActionSell sell) throws Exception {
        String id = target.getId();
        AtomicInteger counter = queueStorage.get(id);
        if (null == counter) {
            AtomicInteger aim = new AtomicInteger();
            AtomicInteger yes = queueStorage.putIfAbsent(id, aim);
            if (null == yes) {
                counter = aim;
            } else {
                counter = yes;
            }
        }
        if (counter.get() + sell.getCount() > target.getQueueSize()) {
            return false;
        }
        counter.getAndAdd(sell.getCount());
        doProducer(target, sell);
        return true;
    }

    public void start() {
        if (running) {
            throw new RuntimeException("can not start it twice");
        }
        try {
            doStart();
        } catch (Exception ex) {
            log.error("", ex);
        }

        //new async thread
        new Thread(() -> {
            try {
                this.consumer();
            } catch (Exception e) {
                log.error("", e);
            }
        }).start();
        this.running = true;
    }

    protected abstract void doStart() throws Exception;

    public abstract void stop() throws Exception;

    protected abstract void doProducer(Target target, ActionSell sell) throws Exception;

    public abstract void consumer() throws Exception;
}
