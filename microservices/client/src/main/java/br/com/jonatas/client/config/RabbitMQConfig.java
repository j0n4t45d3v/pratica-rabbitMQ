package br.com.jonatas.client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jonatas.interfaces.RabbitMQConst.*;

@Configuration
public class RabbitMQConfig {

  @Bean
  Queue msgQueue() {
    return new Queue(QUEUE_MESSAGE);
  }

  @Bean
  Queue replyQueue() {
    return new Queue(QUEUE_REPLY_MESSAGE);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  Binding msgBinding() {
    return BindingBuilder
            .bind(msgQueue())
            .to(exchange())
            .with(QUEUE_MESSAGE);
  }

  @Bean
  Binding replyBinding() {
    return BindingBuilder
            .bind(replyQueue())
            .to(exchange())
            .with(QUEUE_REPLY_MESSAGE);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setReplyAddress(QUEUE_REPLY_MESSAGE);
    template.setReplyTimeout(6000);
    return template;
  }

  @Bean
  SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(QUEUE_REPLY_MESSAGE);
    container.setMessageListener(rabbitTemplate(connectionFactory));
    return container;
  }
}
