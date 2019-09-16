package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QeueProcessorJvm extends QueueProcessor {

    /**
     * 注意是无界队列。队列的长度是在target设置。
     */
    private LinkedBlockingQueue queue;


    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            10, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
    );


    @Override
    protected void doStart() throws Exception {
        queue = new LinkedBlockingQueue();
    }

    @Override
    public void stop() throws Exception {
        this.running = false;
        threadPool.shutdown();
    }

    @Override
    protected void doProducer(Target target, ActionSell sell) throws Exception {
        queue.put(new Entry(target, sell));
    }

    @Override
    public void consumer() throws Exception {
        while (running) {
            final Entry e = (Entry) queue.take();
            threadPool.execute(() -> {
                Holder.getInstance()
                        .getStockService()
                        .subStockNumber(e.target, e.sell.getCount());
            });
        }
    }
}
