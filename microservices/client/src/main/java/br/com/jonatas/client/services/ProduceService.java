package br.com.jonatas.client.services;

import org.jonatas.interfaces.RabbitMQConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.jonatas.interfaces.RabbitMQConst.EXCHANGE;
import static org.jonatas.interfaces.RabbitMQConst.QUEUE_MESSAGE;

@Service
public class ProduceService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public String sendMessage(String message) {
    Message msg = MessageBuilder
            .withBody(message.getBytes())
            .build();

    Message result = rabbitTemplate.sendAndReceive(EXCHANGE, QUEUE_MESSAGE, msg);

    String response = "";

    if (result != null) {
      response = new String(result.getBody());
    }

    return response;
  }

}
