package com.sunnyws.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * - @Description:  TestConsumer
 * - @Author qinlang
 * - @Date 2021/6/15
 * - @Version 1.0
 **/
@Component
@RocketMQMessageListener(topic = "test_topic",consumerGroup = "test_consumer_group1",selectorExpression = "test_tag1")
@Slf4j
public class TestConsumerTopic1 implements RocketMQListener<String> {


    @Override
    public void onMessage(String msg) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("消费带test_tag1：{}",msg);
    }
}
