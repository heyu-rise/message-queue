package com.heyu.messagequeue.rabbit;

import java.io.IOException;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

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

	@Bean
	public MessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue stringHeyu() {
		return new Queue("heyu-string1", true);
	}

	@Bean
	public Queue objectHeyu() {
		return new Queue("heyu-object1", true);
	}

	@Bean
	public Channel channel(ConnectionFactory connectionFactory) throws IOException {
		Channel channel = connectionFactory.createConnection().createChannel(false);
		channel.queueBind("heyu-string1", "heyu-topic", "heyu.string");
		channel.queueBind("heyu-object1", "heyu-topic", "heyu.object");
		return channel;
	}

}
