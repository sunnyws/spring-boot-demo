//package com.sunnyws.rocketmq.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
///**
// * - @Description:  TestConsumer
// * - @Author qinlang
// * - @Date 2021/6/15
// * - @Version 1.0
// **/
//@Component
//@RocketMQMessageListener(topic = "test_topic",consumerGroup = "test_consumer_group")
//@Slf4j
//public class TestConsumer2 implements RocketMQListener<String> {
//
//
//    @Override
//    public void onMessage(String msg) {
//        log.info("消费不带tag：{}",msg);
//    }
//}
