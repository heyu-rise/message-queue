package com.heyu.messagequeue.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.heyu.messagequeue.rocket.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/6/4
 */
@Slf4j
@Component
public class RabbitMqListener {


    @RabbitListener(queues = "heyu-string1")
    public void test1(String str){
        log.info(str);
    }

    @RabbitListener(queues = "heyu-object1")
    public void test2(User user){
        log.info(JsonUtil.obj2json(user));
    }

}
