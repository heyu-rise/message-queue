package com.heyu.messagequeue.active;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2020/5/25
 */
@Component
@Configuration
public class ActiveMqConfig {

	@Bean(name = "topicFactory")
	public JmsListenerContainerFactory<?> topicFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configure) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configure.configure(factory, connectionFactory);
		// 是否为订阅注册模式
		factory.setPubSubDomain(true);
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}

	/**
	 * @return jackson序列化
	 */
	@Bean(name = "jacksonJmsMessageConverter")
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean(name = "heyuTopicObject")
	public Topic heyuTopicObject() {
		return () -> "heyu-topic-object";
	}

	@Bean(name = "heyuTopicString")
	public Topic heyuTopicString() {
		return () -> "heyu-topic-string";
	}
}
