package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.components.stock.StockService;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JvmQueueProcessor implements QueueProcessor {
    private Map<String, AtomicInteger> queueStorage;
    private LinkedBlockingQueue queue;
    private volatile boolean running = false;


    StockService stockService;

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            10, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
    );


    public JvmQueueProcessor(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void start() throws Exception {
        if (running) {
            throw new RuntimeException("can not start it twice");
        }
        queueStorage = new ConcurrentHashMap<>();
        queue = new LinkedBlockingQueue();
        this.running = true;

        new Thread(() -> {
            try {
                this.consumer();
            } catch (Exception e) {
                log.error("", e);
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        this.running = false;
        threadPool.shutdown();
    }

    @Override
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
        queue.put(new Entry(target, sell));
        return true;
    }

    @Override
    public void consumer() throws Exception {
        while (running) {
            final Entry pair = (Entry) queue.take();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    stockService.subStockNumber(pair.target, pair.sell.getCount());
                }
            });
        }
    }
}
