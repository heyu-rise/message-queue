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
public class RabbitMqQueueListener {

	@RabbitListener(queues = "heyu-queue-string")
	public void test1(String str) {
		log.info("1");
		log.info(str);
	}

	@RabbitListener(queues = "heyu-queue-object")
	public void test2(User user) {
		log.info("2");
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(queues = "heyu-queue-string")
	public void test7(String str) {
		log.info("3");
		log.info(str);
	}

	@RabbitListener(queues = "heyu-queue-object")
	public void test8(User user) {
		log.info("4");
		log.info(JsonUtil.obj2json(user));
	}

}
