package com.heyu.messagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author heyu
 */
@EnableKafka
@SpringBootApplication
public class MessageQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueApplication.class, args);
	}



}
