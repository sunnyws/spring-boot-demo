package com.sunnyws.rocketmq.controller;

import com.alibaba.fastjson.JSON;
import com.sunnyws.rocketmq.entity.MqMsg;
import com.sunnyws.rocketmq.producer.RocketMQMessageSenderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * - @Description:  TestController
 * - @Author qinlang
 * - @Date 2021/6/15
 * - @Version 1.0
 **/

@RestController
public class TestController {

    @Resource
    private RocketMQMessageSenderService senderService;

    @RequestMapping("/sendMsg")
    public void sendMsg(){
        MqMsg message = new MqMsg("test_topic","test_tag", "test_msg");
        senderService.asyncSend(message);
    }
}
