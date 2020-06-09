package com.heyu.messagequeue.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heyu.messagequeue.model.User;
import com.heyu.messagequeue.utils.JsonUtil;

/**
 * @author heyu
 * @date 2020/5/27
 */
@RestController
@RequestMapping("/kafka")
public class KafkaTestControl {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@GetMapping("/string")
	public void string() {
		kafkaTemplate.send("heyu-string", "heyu-string");
	}

	@GetMapping("/object")
	public void object() {
		User user = new User("heyu", 78);
		kafkaTemplate.send("heyu-object", JsonUtil.obj2json(user));
	}

}
