[TOC]
# 消息队列实现

## 支持的消息队列

- ActiveMq
- RabbitMq
- RocketMq
- kafka

## 各个队列实现队列与广播模式的方法

### ActiveMq

#### 添加依赖（用的是SpringBoot提供的jms包）

```xml
        <!--activemq-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
        </dependency>
```



#### 实现发送对象

ActiveMq要实现发送对象的话，需要注入序列化类

```java
	@Bean(name = "jacksonJmsMessageConverter")
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
```

然后这个序列化类会替换掉默认的，而且jackson也是支持字符串的

#### 队列模式

ActiveMq默认的为队列模式，发送实现为

```java
jmsTemplate.convertAndSend("heyu-test-string", "heyu-test");
jmsTemplate.convertAndSend("heyu-test-object", new User("jack", 25));
```

消费实现为

```java
	@JmsListener(destination = "heyu-test-string")
	public void stringListener(String message) {
		log.info(message);
	}

	@JmsListener(destination = "heyu-test-object")
	public void objectListener(User user) {
		log.info(JsonUtil.obj2json(user));
	}
```

#### 广播模式

要实现广播模式，需要先注入广播模式的JmsListenerContainerFactory， 序列化对象也是jackson

```java
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
```

发送实现为

```java
Topic heyuTopicString =  () -> "heyu-topic-string";
Topic heyuTopicObject =  () -> "heyu-topic-object";
jmsTemplate.send(heyuTopicString, session -> jacksonJmsMessageConverter.toMessage("topic-string", session));
jmsTemplate.send(heyuTopicObject, session -> jacksonJmsMessageConverter.toMessage(new User("jack", 26), session));
```

消费实现为

```java
	@JmsListener(destination = "heyu-topic-string", containerFactory = "topicFactory")
	public void topicString1(String user) {
		log.info(user);
	}

	@JmsListener(destination = "heyu-topic-object", containerFactory = "topicFactory")
	public void topicObject1(User user) {
		log.info(JsonUtil.obj2json(user));
	}
```

