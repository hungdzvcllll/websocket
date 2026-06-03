package com.example.demo.message;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.MessageRequestDto;
import com.example.demo.service.MessagesService;

@Controller
public class MessageController {
    @Autowired
    MessagesService service;
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat")
    public void send(MessageRequestDto mes,Principal prin){
        String senderName=prin.getName();
        service.saveMessage(senderName,mes.getReceivedName(),mes.getContent());
        messagingTemplate.convertAndSendToUser(
                mes.getReceivedName(),
                "/queue/messages",
                mes
        );
    }

}
