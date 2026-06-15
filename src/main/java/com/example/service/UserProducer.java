package com.example.service;

import com.example.entity.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserProducer {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void publish(UserEvent event) {

        CompletableFuture<SendResult<String, UserEvent>> future = kafkaTemplate.send(
                "user-events",
                String.valueOf(event.getUserId()),
                event
        );
        future.whenComplete((result,ex) ->{
            if(ex == null){
                System.out.println(" Sent Message successfully: "+result.getRecordMetadata().offset());
            }else {
                System.out.println("Unable to sent message: "+ex.getMessage());
            }
        });
    }
}
