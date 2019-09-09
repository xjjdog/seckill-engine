package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class QueueProcessorKafka extends QueueProcessor {
    Producer producer;
    Consumer consumer;

    @Getter
    @Setter
    String topic;

    public void configure(Properties properties) {
        this.check(properties);
        producer = new KafkaProducer(properties);
        consumer = new KafkaConsumer(properties);
    }

    /**
     * 检查配置参数
     *
     * @throws Exception
     */
    private void check(Properties properties) {
    }

    @Override
    protected void doStart() throws Exception {
        consumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public void stop() throws Exception {
        if (null != producer) {
            producer.flush();
            producer.close();
        }
    }

    @Override
    protected void doProducer(Target target, ActionSell sell) throws Exception {
        String str = new Entry(target, sell).toCacheString();
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, str);
        //no callback, no future, to cache quickly
        producer.send(record);
    }

    @Override
    public void consumer() throws Exception {
        while (running) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.count() > 0) {
                records.forEach(record -> {
                    String value = record.value();
                    try {
                        Entry e = Entry.fromString(value);
                        Holder.getInstance()
                                .getStockService()
                                .subStockNumber(e.target, e.sell.getCount());
                    } catch (Exception ex) {
                        log.error("parse error", ex);
                    }
                });
            }
        }
    }
}
