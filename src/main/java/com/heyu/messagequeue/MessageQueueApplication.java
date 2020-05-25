package com.heyu.messagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyu
 */
@RestController
@SpringBootApplication
public class MessageQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueApplication.class, args);
	}



}
