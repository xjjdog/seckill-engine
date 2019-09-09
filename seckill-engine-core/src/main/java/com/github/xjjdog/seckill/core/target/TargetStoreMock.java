package com.github.xjjdog.seckill.core.target;

import java.util.UUID;

public class TargetStoreMock implements TargetStore {
    static final int InitStock = 100;
    static final int QueueSize = 100;

    @Override
    public Target getTarget(String targetID) {
        Target target = new Target();
        target.setId(UUID.randomUUID().toString());
        target.setStock(InitStock);
        target.setQueueSize(QueueSize);

        return target;
    }
}
