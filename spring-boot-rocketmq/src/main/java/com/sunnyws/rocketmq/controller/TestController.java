package com.sunnyws.rocketmq.controller;

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

        for(int i = 1;i<=100;i++){
            //不带topic
            MqMsg message = new MqMsg("test_topic" ,"","test_msg");
            senderService.send(message);
        }

    }

    @RequestMapping("/sendMsgTopic")
    public void sendMsgTopic(){

        for(int i = 1;i<=2000;i++){
            //不带topic
            MqMsg message = new MqMsg("test_topic" ,"test_tag1",String.valueOf(i));
            senderService.asyncSend(message);
        }

        for(int i = 1;i<=2000;i++){
            //不带topic
            MqMsg message = new MqMsg("test_topic" ,"test_tag2",String.valueOf(i));
            senderService.asyncSend(message);
        }

    }


}
