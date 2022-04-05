package com.example.rs.mq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "Queue1")
public class TestReceiver {
    @RabbitHandler
    public void QueueReceiver(String queue1) {
        System.err.println(queue1);
    }
}
