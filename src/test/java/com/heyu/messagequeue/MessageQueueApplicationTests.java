package com.heyu.messagequeue;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.Topic;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import com.heyu.messagequeue.model.User;

@SpringBootTest
class MessageQueueApplicationTests {

	@Value("${heyu.rocketmq.topic.string}")
	private String stringTopic;

	@Value("${heyu.rocketmq.topic.object}")
	private String objectTopic;

	@Resource
	private RocketMQTemplate rocketMQTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Test
	void contextLoads() {
		active();
	}

	private void rocket() {
		rocketMQTemplate.syncSend(stringTopic, "stringTopic1");
		rocketMQTemplate.syncSend(objectTopic, new User("heyu", 25));
	}

	private void active() {
		jmsTemplate.convertAndSend("heyu-test-string", "heyu-test");
		jmsTemplate.convertAndSend("heyu-test-object", new User("heyu", 25));
		Topic topic = () -> "heyu-topic";
		jmsTemplate.send(topic, session -> {
			Message message = session.createMessage();
			message.setStringProperty("name", "string");
			return message;
		});

	}

}
