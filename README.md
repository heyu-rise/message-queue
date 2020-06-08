[TOC]
# 消息队列实现

## 支持的消息队列

- ActiveMq
- RabbitMq
- RocketMq
- Kafka

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

#### 配置文件

```yaml
spring:
  activemq:
    broker-url: tcp://127.0.0.1:61616
    password: admin
    user: admin
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

### RabbitMq

#### 添加依赖（springboot的amqp包）

```xml
        <!--rabbitmq-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```

#### 配置文件

```yaml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 3456
    username: admin
    password: 123456
```

#### 队列配置

```java
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonConverter());
		return template;
	}

	/**
	 * 只要注入MessageConverter，就会替换掉默认的，要实现发送对象，必须先注入新的序列化对象
	 * 
	 * @return jackson序列化方式
	 */
	@Bean
	public MessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * 队列需要优先注入创建，否则广播接受的时候会报错
	 */
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
```

#### 队列模式

发送实现

```java
	rabbitTemplate.convertAndSend("heyu-queue-string", "heyu-string");
	rabbitTemplate.convertAndSend("heyu-queue-object", new User("heyu", 25));
```

接收实现

```java
	@RabbitListener(queues = "heyu-queue-string")
	public void test1(String str) {
		log.info(str);
	}

	@RabbitListener(queues = "heyu-queue-object")
	public void test2(User user) {
		log.info(JsonUtil.obj2json(user));
	}
```

#### 广播模式

发送实现

```java
	rabbitTemplate.convertAndSend("heyu-fanout-string", "", "heyu-string");
	rabbitTemplate.convertAndSend("heyu-fanout-object", "", new User("heyu", 25));
```

接收实现

```java
	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-string1", durable = "true"), exchange = @Exchange(value = "heyu-fanout-string", type = "fanout")))
	public void test3(String str) {
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-string2", durable = "true"), exchange = @Exchange(value = "heyu-fanout-string", type = "fanout")))
	public void test5(String str) {
		log.info(str);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-object1", durable = "true"), exchange = @Exchange(value = "heyu-fanout-object", type = "fanout")))
	public void test4(User user) {
		log.info(JsonUtil.obj2json(user));
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "heyu-fanout-object2", durable = "true"), exchange = @Exchange(value = "heyu-fanout-object", type = "fanout")))
	public void test6(User user) {
		log.info(JsonUtil.obj2json(user));
	}
```

### RocketMq

#### 添加依赖

```xml
        <!--rocketmq-->
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
```

#### 配置文件

```yaml
rocketmq:
  name-server: 1270.0.1:9876
  producer:
    group: heyu-producer
    sendMessageTimeout: 300000
```

#### 队列广播实现

> RocketMq用组的方式管理消息，没有具体的队列与广播方式的区别，同一个topic下，相同的组消费同一条消息，不同的组共享消息，而且在同一个服务下，不能存在相同的组，如果同一个组要添加消费者，只能通过集群的方式来实现

发送实现

```java
rocketMQTemplate.syncSend(stringTopic, "stringTopic1");
rocketMQTemplate.syncSend(objectTopic, new User("heyu", 25));
```

接受实现

```java
@Service
@RocketMQMessageListener(topic = "${heyu.rocketmq.topic.string}", consumerGroup = "heyu-string1")
public class ConsumeStringLister implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info(message);
    }
}

@Service
@RocketMQMessageListener(topic = "${heyu.rocketmq.topic.object}", consumerGroup = "heyu-object1")
public class ConsumeObjectLister implements RocketMQListener<User> {
	@Override
	public void onMessage(User message) {
		log.info(JsonUtil.obj2json(message));
	}
}
```

### Kafka

#### 添加依赖

```xml
        <!--kafka-->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
```

#### 配置文件

```yaml
kafka:
  bootstrap-server: 127.0.0.1:9092
```

#### 队列配置

生产者

```java
@Component
@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.bootstrap-server}")
	private String bootstrapServer;

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	private ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	private Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>(10);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
}
```

消费者

```java
@Component
@Configuration
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap-server}")
	private String bootstrapServer;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	private ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	private Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>(10);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

}
```

#### 队列广播实现

> kafka与rocketMq一样，都是通过组来实现队列与广播，而且逻辑完全相同，只不过kafka没有一个服务内不能有相同组的限制

发送实现

```java
kafkaTemplate.send("heyu-string", "heyu-string");
```

接受实现

```java
    @KafkaListener(topics = "heyu-string", groupId = "heyu1")
    public void string(String message){
        log.info(message);
    }

    @KafkaListener(topics = "heyu-string", groupId = "heyu2")
    public void string2(String message){
        log.info(message);
    }
```

