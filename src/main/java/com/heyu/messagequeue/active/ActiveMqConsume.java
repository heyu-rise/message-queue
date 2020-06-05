package com.heyu.messagequeue.active;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.heyu.messagequeue.rocket.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

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
		log.info(JsonUtil.obj2json(user));
	}

	@JmsListener(destination = "heyu-topic-string", containerFactory = "topicFactory")
	public void topicString1(String user) {
		log.info(user);
	}

	@JmsListener(destination = "heyu-topic-object", containerFactory = "topicFactory")
	public void topicObject1(User user) {
		log.info(JsonUtil.obj2json(user));
	}

	@JmsListener(destination = "heyu-topic-string", containerFactory = "topicFactory")
	public void topicString2(String user) {
		log.info(user);
	}

	@JmsListener(destination = "heyu-topic-object", containerFactory = "topicFactory")
	public void topicObject2(User user) {
		log.info(JsonUtil.obj2json(user));
	}
}
