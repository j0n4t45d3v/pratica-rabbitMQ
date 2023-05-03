package com.microservice.estoquepreco.connections;

import constantes.RabbitMQConstantes;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
    private static final String NOME_EXCHANGE = "amq.direct";

    @Autowired
    private AmqpAdmin admin;

    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(){
    return  new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }


    @PostConstruct
    private void adiciona(){
        Queue filaEstoque = this.fila(RabbitMQConstantes.FILA_ESTOQUE);
        Queue filaPreco = this.fila(RabbitMQConstantes.FILA_PRECO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
        Binding ligacaoPreco = this.relacionamento(filaPreco, troca);


//        Criando as filas no RabbitMQ
        this.admin.declareQueue(filaEstoque);
        this.admin.declareQueue(filaPreco);

        this.admin.declareExchange(troca);

        this.admin.declareBinding(ligacaoEstoque);
        this.admin.declareBinding(ligacaoPreco);
    }

}


