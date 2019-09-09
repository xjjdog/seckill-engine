package com.github.xjjdog.seckill.core;

import com.github.xjjdog.seckill.core.target.Target;

public class Factory {
    public static final int InitStock = 100;
    public static final int QueueSize = 100;

    public static final Target getTarget() {
        Target target = new Target();
        target.setId("1");
        target.setStock(InitStock);
        target.setQueueSize(QueueSize);

        return target;
    }
}
