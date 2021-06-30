package com.sunnyws.rocketmq.producer;

import com.sunnyws.rocketmq.entity.MqMsg;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: chinaictc-sc-parent
 * @description:
 * @author: zcl
 * @create: 2020-12-24 14:59
 **/
@Service
@Log4j2
public class RocketMQMessageSenderServiceImpl implements RocketMQMessageSenderService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void send(MqMsg mqMsg) {
//        log.info("send发送消息到mqMsg={}", mqMsg);
        rocketMQTemplate.send(mqMsg.getTopic() + ":" + mqMsg.getTags(),
                MessageBuilder.withPayload(mqMsg).build());

    }
    @Override
    public void asyncSend(MqMsg mqMsg) {
//        log.info("asyncSend发送消息到mqMsg={}", mqMsg);
        rocketMQTemplate.asyncSend(mqMsg.getTopic() + ":" + mqMsg.getTags(), mqMsg.getContent(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        // 成功不做日志记录或处理
                        log.info("mqMsg消息发送成功");
                    }
                    @Override
                    public void onException(Throwable throwable) {
                        throwable.printStackTrace();
                        log.info("mqMsg消息发送失败");
                    }
                });
    }
    @Override
    public void syncSendOrderly(MqMsg mqMsg) {
//        log.info("syncSendOrderly发送消息到mqMsg={}", mqMsg);
        rocketMQTemplate.sendOneWay(mqMsg.getTopic() + ":" + mqMsg.getTags(), mqMsg);
    }
}
