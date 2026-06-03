package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MessagesService;

@RestController
public class MessageController {
    @Autowired
    MessagesService service;
    @GetMapping("/sendedMessage")
    public ResponseEntity<?> sendedMessage(@RequestParam("username")String username){
        try{
            return ResponseEntity.ok(service.yourSendMessageTo(username));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/receivedMessage")
    public ResponseEntity<?> receivedMessage(@RequestParam("username")String username){
        try{
            return ResponseEntity.ok(service.yourReceivedMessage(username));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
