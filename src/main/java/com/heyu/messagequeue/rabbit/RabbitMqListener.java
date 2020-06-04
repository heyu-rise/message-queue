package com.heyu.messagequeue.rabbit;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
	public void test1(String str) {
		log.info("1");
		log.info(str);
	}

	@RabbitListener(queues = "heyu-object1")
	public void test2(User user) {
		log.info("2");
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-string1", durable = "true"), exchange = @Exchange(value = "heyu-topic", type = "topic"), key = "heyu.string"))
	public void test3(String str) {
		log.info("3");
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-object1", durable = "true"), exchange = @Exchange(value = "heyu-topic", type = "topic"), key = "heyu.object"))
	public void test4(User user) {
		log.info("4");
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-string1", durable = "true"), exchange = @Exchange(value = "heyu-topic", type = "topic"), key = "heyu.string"))
	public void test5(String str) {
		log.info("5");
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-object1", durable = "true"), exchange = @Exchange(value = "heyu-topic", type = "topic"), key = "heyu.object"))
	public void test6(User user) {
		log.info("6");
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(queues = "heyu-string1")
	public void test7(String str) {
		log.info("7");
		log.info(str);
	}

	@RabbitListener(queues = "heyu-object1")
	public void test8(User user) {
		log.info("8");
		log.info(JsonUtil.obj2json(user));
	}

}
