package com.estoquepreco.microservico.connections;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import static constants.RabbitMQConstants.*;

@Component
public class RabbitMQConnection {
    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue (String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    //Anotação para o spring chamar o método quando inicializar a aplicação.
    @PostConstruct
    private void add() {
        Queue stockQueue = this.queue(QUEUE_STOCK);
        Queue priceQueue = this.queue(QUEUE_PRICE);

        DirectExchange exchange = this.directExchange();

        Binding stockBinding = this. binding(stockQueue, exchange);
        Binding priceBinding = this. binding(priceQueue, exchange);

        this.amqpAdmin.declareQueue(stockQueue);
        this.amqpAdmin.declareQueue(priceQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(stockBinding);
        this.amqpAdmin.declareBinding(priceBinding);
    }
}
