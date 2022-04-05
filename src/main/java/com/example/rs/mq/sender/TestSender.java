package com.example.rs.mq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSender {
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void send(String content) {
        System.err.println("Sender: " + content);
        amqpTemplate.convertAndSend("Queue1", content);
    }
}
