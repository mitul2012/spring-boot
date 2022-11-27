package com.practice.demo.producer;

import com.practice.demo.data.User;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String,User> kafkaTemplate;

    @Value("${kafka.topic}")
    String topic;

    public void sendMessage(User msg) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String,User>> metadata =  kafkaTemplate.send(topic,msg);
        RecordMetadata data = metadata.get().getRecordMetadata();
        System.out.println("PRODUCER : "+data.topic()+"  --PARTITION : "+ data.partition()+"   --OFFSET : "+data.offset()+"   --TIMESTAMP : "+data.timestamp());

    }
}
