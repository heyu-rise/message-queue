package com.heyu.messagequeue.active;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.heyu.messagequeue.rocket.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/5/25
 */
@Slf4j
@Component
public class ActiveMqConsume {

	@JmsListener(destination = "heyu-test-string")
	public void stringListener(String message) {
		log.info(message);
	}

	@JmsListener(destination = "heyu-test-object")
	public void objectListener(User user) {
		log.info(JSON.toJSONString(user));
	}

	@JmsListener(destination = "heyu-topic", containerFactory = "topicFactory")
	public void string(User user) {
		log.info(JSON.toJSONString(user));
	}
}
