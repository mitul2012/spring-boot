package com.practice.demo.consumer;


import com.practice.demo.data.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic}")
    public void consume(Message<User> msg){
        System.out.println("CONSUMER : "+msg.getPayload().toString()+"   --HEADERS : "+msg.getHeaders());
    }
}
