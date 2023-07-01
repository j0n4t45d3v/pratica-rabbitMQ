package br.com.jonatas.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
}
