package com.heyu.messagequeue.rocket;

import com.heyu.messagequeue.rocket.model.User;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyu
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/rocketmq")
public class RocketMqTestControl {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${heyu.rocketmq.topic.string}")
    private String stringTopic;

    @Value("${heyu.rocketmq.topic.object}")
    private String objectTopic;


    @GetMapping("/string")
    public void string() {
        rocketMQTemplate.syncSend(stringTopic, "stringTopic1");
    }

    @GetMapping("/object")
    public void object() {
        rocketMQTemplate.syncSend(objectTopic, new User("heyu", 25));
    }
}
