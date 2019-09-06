package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;

public interface QueueProcessor {
    void start() throws Exception;

    void stop() throws Exception;

    boolean producer(Target target, ActionSell sell) throws Exception;

    void consumer() throws Exception;
}
