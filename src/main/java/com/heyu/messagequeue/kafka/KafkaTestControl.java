package com.heyu.messagequeue.kafka;

import com.heyu.messagequeue.rocket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void string(){
        kafkaTemplate.send("heyu-string", "heyu-string");
    }

    @GetMapping("/object")
    public void object(){
        kafkaTemplate.send("heyu-object", new User("heyu", 26));
    }

}
