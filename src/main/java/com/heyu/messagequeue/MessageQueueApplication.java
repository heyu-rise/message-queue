package com.heyu.messagequeue;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heyu.messagequeue.model.User;

/**
 * @author heyu
 */
@RestController
@SpringBootApplication
public class MessageQueueApplication {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	@Value("${heyu.rocketmq.topic.string}")
	private String stringTopic;

	@Value("${heyu.rocketmq.topic.object}")
	private String objectTopic;

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueApplication.class, args);
	}

	@GetMapping("/string")
	public void string() {
		rocketMQTemplate.syncSend(stringTopic, "stringTopic1");
	}

	@GetMapping("/object")
	public void object() {
		rocketMQTemplate.syncSend(objectTopic, new User("heyu", 25));
	}

}
