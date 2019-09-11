package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

@Slf4j
public class QeueProcessorDisruptor extends QueueProcessor {
    private static final int RingBufferSize = 16;
    private RingBuffer<ValueEvent> ringBuffer;

    public static class ValueEvent {
        Entry value;
        public final static EventFactory EVENT_FACTORY = () -> new ValueEvent();
    }


    public EventHandler<ValueEvent>[] getEventHandler() {
        EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> {
            Entry e = event.value;
            Holder.getInstance()
                    .getStockService()
                    .subStockNumber(e.target, e.sell.getCount());
        };
        return new EventHandler[]{eventHandler};
    }


    ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
    WaitStrategy waitStrategy = new BusySpinWaitStrategy();
    Disruptor<ValueEvent> disruptor = new Disruptor<>(
            ValueEvent.EVENT_FACTORY,
            RingBufferSize,
            threadFactory,
            ProducerType.SINGLE,
            waitStrategy);

    @Override
    protected void doStart() throws Exception {
        disruptor.handleEventsWith(getEventHandler());
        ringBuffer = disruptor.start();
    }

    @Override
    public void stop() throws Exception {
        this.running = false;
        if (null != this.disruptor) {
            this.disruptor.shutdown();
        }
    }

    @Override
    protected void doProducer(Target target, ActionSell sell) throws Exception {
        long sequenceId = ringBuffer.next();
        ValueEvent valueEvent = ringBuffer.get(sequenceId);
        valueEvent.value = new Entry(target, sell);
        ringBuffer.publish(sequenceId);
    }

    @Override
    public void consumer() throws Exception {
        log.debug("is use the ring buffer system");
    }
}
