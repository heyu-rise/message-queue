package com.heyu.messagequeue;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.heyu.messagequeue.model.User;

@SpringBootTest
class MessageQueueApplicationTests {

	@Value("${heyu.rocketmq.topic.string}")
	private String stringTopic;

	@Value("${heyu.rocketmq.topic.object}")
	private String objectTopic;

	@Resource
	private RocketMQTemplate rocketMQTemplate;

	@Test
	void contextLoads() throws UnsupportedEncodingException {
		rocketMQTemplate.syncSend(stringTopic, "stringTopic1");
		rocketMQTemplate.syncSend(objectTopic, new User("heyu", 25));
	}

}
