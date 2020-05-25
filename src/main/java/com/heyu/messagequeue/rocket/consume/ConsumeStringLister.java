package com.heyu.messagequeue.rocket.consume;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/5/18
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${heyu.rocketmq.topic.string}", consumerGroup = "heyu")
public class ConsumeStringLister implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info(message);
    }
}
