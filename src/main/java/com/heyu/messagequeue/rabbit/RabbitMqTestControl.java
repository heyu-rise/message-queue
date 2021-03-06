package com.heyu.messagequeue.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heyu.messagequeue.model.User;

/**
 * @author heyu
 * @date 2020/6/4
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitMqTestControl {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/queue")
	public void queue() {

		rabbitTemplate.convertAndSend("heyu-queue-string", "heyu-string");

		rabbitTemplate.convertAndSend("heyu-queue-object", new User("heyu", 25));
	}

	@GetMapping("/topic")
	public void topic() {

		rabbitTemplate.convertAndSend("heyu-fanout-string", "", "heyu-string");

		rabbitTemplate.convertAndSend("heyu-fanout-object", "", new User("heyu", 25));
	}

}
