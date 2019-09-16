package com.github.xjjdog.seckill.core;

import com.github.xjjdog.seckill.core.components.queue.QeueProcessorJvm;
import com.github.xjjdog.seckill.core.components.queue.QueueProcessor;
import com.github.xjjdog.seckill.core.components.queue.QueueProcessorKafka;
import com.github.xjjdog.seckill.core.components.stock.StockService;
import com.github.xjjdog.seckill.core.components.stock.StockServiceMock;

public final class Holder {
    private static Holder holder = new Holder();

    private Holder() {
    }

    public static final Holder getInstance() {
        return holder;
    }

    public StockService getStockService() {
        return stockService;
    }

    public TargetService getTargetService() {
        return targetService;
    }

    public QueueProcessor getQueueProcessor() {
        return queueProcessor;
    }

    private StockService stockService = new StockServiceMock();
    private TargetService targetService = new TargetService();
    private QueueProcessor queueProcessor = new QeueProcessorJvm();
}
