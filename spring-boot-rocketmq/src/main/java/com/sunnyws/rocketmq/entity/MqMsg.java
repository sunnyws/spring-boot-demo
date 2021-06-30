package com.sunnyws.rocketmq.entity;

import lombok.Data;

/**
 * - @Description:  MqMsg
 * - @Author qinlang
 * - @Date 2021/6/15
 * - @Version 1.0
 **/

@Data
public class MqMsg {
    /**
     * 一级消息：消息topic
     */
    private String topic;
    /**
     * 二级消息：消息topic对应的tags
     */
    private String tags;
    /**
     * 消息内容
     */
    private String content;

    public MqMsg() {
    }
    public MqMsg(String topic, String tags, String content) {
        this.topic = topic;
        this.tags = tags;
        this.content = content;
    }
}