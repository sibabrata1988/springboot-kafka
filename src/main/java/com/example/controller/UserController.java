package com.example.controller;

import com.example.entity.UserEvent;
import com.example.service.UserProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProducer producer;

    public UserController(UserProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/event")
    public ResponseEntity<?> send(@RequestBody UserEvent event) {
        try {
            producer.publish(event);
            return ResponseEntity.ok(event);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
