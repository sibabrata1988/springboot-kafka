package com.example.service;

import com.example.entity.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    @RetryableTopic(attempts = "5",
        backoff = @Backoff(delay =1000, multiplier = 2.0)
    )
    @KafkaListener(
            topics = "user-events",
            groupId = "user-group"
    )
    public void consume(ConsumerRecord<String, UserEvent> record) {

        String key = record.key();
        UserEvent event = record.value();
        System.out.println("Key  :"+key);
        System.out.println("User Id :"+event.getUserId());
        System.out.println("User Name :"+event.getUserName());
        System.out.println("User Action :"+event.getAction());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset   : " + record.offset());
        System.out.println("Topic    : " + record.topic());
        System.out.println("Timestamp: " + record.timestamp());
        if (event.getUserId()==123L){
            System.out.println("Exception occured");
            throw new RuntimeException("Exception occurred processing event");
        }

    }
    @DltHandler
    public void handleDLT(ConsumerRecord<String, UserEvent> record){
        System.out.println("=======DLT Message=======");
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset   : " + record.offset());
        System.out.println("Topic    : " + record.topic());

    }
}
