package com.heyu.messagequeue.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.heyu.messagequeue.rocket.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/5/27
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "heyu-string", groupId = "heyu1")
    public void string(String message){
        log.info(message);
    }

    @KafkaListener(topics = "heyu-string", groupId = "heyu2")
    public void string2(String message){
        log.info(message);
    }

    @KafkaListener(topics = "heyu-object", groupId = "object-heyu1")
    public void object(User user){
        log.info(JsonUtil.obj2json(user));
    }

}
