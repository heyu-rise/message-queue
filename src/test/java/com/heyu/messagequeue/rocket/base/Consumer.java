package com.heyu.messagequeue.rocket.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 消费消息
 *
 * @author heyu
 * @date 2020/5/18
 */
public class Consumer {
	public static void main(String[] args) throws MQClientException {
		// 实例化消费者
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("heyu");

		// 设置NameServer的地址
		consumer.setNamesrvAddr("47.93.30.98:9876");

		// 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
		consumer.subscribe("TopicTest", "*");
		// 注册回调实现类来处理从broker拉取回来的消息
		consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
			System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msg);
			// 标记该消息已经被成功消费
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		// 启动消费者实例
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
