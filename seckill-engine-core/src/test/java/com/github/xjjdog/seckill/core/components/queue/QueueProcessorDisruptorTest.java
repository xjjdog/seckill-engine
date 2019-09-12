package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.components.stock.StockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueProcessorDisruptorTest extends QueueProcessorJvmTest {
    @Override
    protected QueueProcessor getQueueProcessor() {
        return new QeueProcessorDisruptor();
    }

    @Override
    protected StockService getStockService() {
        return Holder.getInstance().getStockService();
    }
}
