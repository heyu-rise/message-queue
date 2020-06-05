package com.heyu.messagequeue.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2020/6/4
 */
@Component
@Configuration
public class RabbitMqConfig {

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonConverter());
		return template;
	}

	/**
	 * 只要注入MessageConverter，就会替换掉默认的
	 * 
	 * @return jackson序列化方式
	 */
	@Bean
	public MessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue a() {
		return new Queue("heyu-queue-string", true);
	}

	@Bean
	public Queue b() {
		return new Queue("heyu-queue-object", true);
	}

	@Bean
	public Queue c() {
		return new Queue("heyu-fanout-object1", true);
	}

	@Bean
	public Queue s() {
		return new Queue("heyu-fanout-object2", true);
	}

	@Bean
	public Queue f() {
		return new Queue("heyu-fanout-string1", true);
	}

	@Bean
	public Queue g() {
		return new Queue("heyu-fanout-string2", true);
	}

}
