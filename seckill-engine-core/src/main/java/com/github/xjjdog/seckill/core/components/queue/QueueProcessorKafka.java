package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Slf4j
public class QueueProcessorKafka implements QueueProcessor {
    Producer producer;
    Consumer consumer;
    String topic;

    public void configure(Properties properties) {
        producer = new KafkaProducer(properties);
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        if (null != producer) {
            producer.flush();
            producer.close();
        }
    }

    @Override
    public boolean producer(Target target, ActionSell sell) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "");
        return false;
    }

    @Override
    public void consumer() throws Exception {

    }
}
