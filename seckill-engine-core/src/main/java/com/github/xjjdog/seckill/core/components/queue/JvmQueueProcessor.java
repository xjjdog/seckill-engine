package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JvmQueueProcessor implements QueueProcessor {
    private Map<String, AtomicInteger> queueStorage;
    private LinkedBlockingQueue queue;
    private volatile boolean running = false;

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            10, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
    );

    /**
     * 缓存数据实体
     */
    class Pair {
        Target target;
        ActionSell sell;

        Pair(Target target, ActionSell sell) {
            this.target = target;
            this.sell = sell;
        }
    }

    @Override
    public void start() throws Exception {
        queueStorage = new ConcurrentHashMap<>();
        queue = new LinkedBlockingQueue();
        this.running = true;

        this.consumer();
    }

    @Override
    public void stop() throws Exception {
        threadPool.shutdown();
    }

    @Override
    public boolean producer(Target target, ActionSell sell) throws Exception {
        String id = target.getId();
        AtomicInteger counter = queueStorage.get(id);
        if (null == counter) {
            counter = queueStorage.putIfAbsent(id, new AtomicInteger());
        }
        if (counter.get() >= target.getQueueSize()) {
            return false;
        }
        counter.getAndIncrement();
        queue.put(new Pair(target, sell));
        return true;
    }

    @Override
    public void consumer() throws Exception {
        while (running) {
            Pair pair = (Pair) queue.poll();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //TODO
                }
            });
        }
    }
}
