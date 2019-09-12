package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.components.stock.StockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueProcessorJvmTest extends AbstractQueueProcessorTest {
    @Override
    protected QueueProcessor getQueueProcessor() {
        return new QeueProcessorJvm();
    }

    @Override
    protected StockService getStockService() {
        return Holder.getInstance().getStockService();
    }

}
