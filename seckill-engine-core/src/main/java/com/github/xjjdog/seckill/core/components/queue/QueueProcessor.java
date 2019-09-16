package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class QueueProcessor {
    /**
     * Key是targetID <br/>
     * Value是targetID对应队列的长度。每一个target都有独立的控制器，都有自己的限制长度，就是在这里控制
     */
    private Map<String, AtomicInteger> queueStorage = new ConcurrentHashMap<>();

    /**
     * 使用volatile关键字来控制线程启动关闭，这是一贯作风
     */
    protected volatile boolean running = false;

    /**
     * 注意其中的counter变量。对于atomic本身来说是原子的，但是加上null判断，可能就不是了。所以putIfAbsent方法很重要
     *
     * @param target
     * @param sell
     * @return
     * @throws Exception
     */
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
        /*队列长度超出了配置长度*/
        if (counter.get() + sell.getCount() > target.getQueueSize()) {
            return false;
        }
        counter.getAndAdd(sell.getCount());
        doProducer(target, sell);
        return true;
    }

    /**
     * 与thread类似。start方法只允许调用一次
     */
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
