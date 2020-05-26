package com.heyu.messagequeue.rocket.consume;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import com.heyu.messagequeue.rocket.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/5/18
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${heyu.rocketmq.topic.object}", consumerGroup = "heyuobject")
public class ConsumeObjectLister implements RocketMQListener<User> {
    @Override
    public void onMessage(User message) {
        log.info(JsonUtil.obj2json(message));
    }
}
