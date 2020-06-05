package com.heyu.messagequeue.active;

import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heyu.messagequeue.model.User;

/**
 * @author heyu
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/activemq")
public class ActiveMqTestControl {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Topic heyuTopicObject;

	@Autowired
	private Topic heyuTopicString;

	@Autowired
	private MessageConverter jacksonJmsMessageConverter;

	@GetMapping("/string")
	public void string() {
		jmsTemplate.convertAndSend("heyu-test-string", "heyu-test");
	}

	@GetMapping("/object")
	public void object() {
		jmsTemplate.convertAndSend("heyu-test-object", new User("heyu", 25));
	}

	@GetMapping("/topic-string")
	public void topicString() {
		jmsTemplate.send(heyuTopicString, session -> jacksonJmsMessageConverter.toMessage("topic-string", session));
	}

	@GetMapping("/topic-object")
	public void topicObject() {
		jmsTemplate.send(heyuTopicObject,
				session -> jacksonJmsMessageConverter.toMessage(new User("heyu", 26), session));
	}

}
