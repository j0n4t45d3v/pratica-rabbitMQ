package br.com.jonatas.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.jonatas.interfaces.RabbitMQConst.*;

@Component
public class ConsumerController {
  private static final Logger LOGGER =
          LoggerFactory.getLogger(ConsumerController.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = QUEUE_MESSAGE)
  void consumeMessage(Message message) {

    byte[] body = message.getBody();
    String msg = new String(body);

    String getMsg = msg.split(":")[1]
            .replace("}", "")
            .replace("\"", "")
            .replaceAll("\\n", "");

    Message msgBuilder = MessageBuilder
            .withBody(("Reply message " + getMsg).getBytes())
            .build();
    LOGGER.info("Message received2: {}", getMsg);

    rabbitTemplate.sendAndReceive(EXCHANGE, QUEUE_REPLY_MESSAGE, msgBuilder);
  }

}
