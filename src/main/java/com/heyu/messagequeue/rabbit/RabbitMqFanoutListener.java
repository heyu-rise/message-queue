package com.heyu.messagequeue.rabbit;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.heyu.messagequeue.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/6/4
 */
@Slf4j
@Component
public class RabbitMqFanoutListener {

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-string1", durable = "true"), exchange = @Exchange(value = "heyu-fanout-string", type = "fanout")))
	public void test3(String str) {
		log.info("1");
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-string2", durable = "true"), exchange = @Exchange(value = "heyu-fanout-string", type = "fanout")))
	public void test5(String str) {
		log.info("2");
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-object1", durable = "true"), exchange = @Exchange(value = "heyu-fanout-object", type = "fanout")))
	public void test4(User user) {
		log.info("3");
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-object2", durable = "true"), exchange = @Exchange(value = "heyu-fanout-object", type = "fanout")))
	public void test6(User user) {
		log.info("4");
		log.info(JsonUtil.obj2json(user));
	}

}
