package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MessagesDto;
import com.example.demo.entity.Messages;
import com.example.demo.entity.User;
import com.example.demo.repository.MessagesRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MessagesService{
    @Autowired
    MessagesRepository mesRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;
    public void saveMessage(String senderName,String receiverName,String content){
        User sender=userRepo.findByUsername(senderName);
        User receiver=userRepo.findByUsername(receiverName);
        mesRepo.save(new Messages(null,content,sender,receiver));
    }
    public ArrayList<MessagesDto> yourSendMessageTo(String username){
        User receiver=userRepo.findByUsername(username);
        User you=userService.findCurrentUser();
        ArrayList<Messages> list=mesRepo.findBySendFromAndSendTo(you, receiver);
        ArrayList<MessagesDto>dto=new ArrayList<MessagesDto>();
        for(Messages m:list){
            dto.add(new MessagesDto(you.getId(),receiver.getId(),you.getUsername(),
            receiver.getUsername(),m.getContent()));
        }
        return dto;

    }
     public ArrayList<MessagesDto> yourReceivedMessage(String username){
        User sender=userRepo.findByUsername(username);
        User you=userService.findCurrentUser();
        ArrayList<Messages> list=mesRepo.findBySendFromAndSendTo(sender,you);
        ArrayList<MessagesDto>dto=new ArrayList<MessagesDto>();
        for(Messages m:list){
            dto.add(new MessagesDto(sender.getId(),you.getId(),sender.getUsername(),
            you.getUsername(),m.getContent()));
        }
        return dto;

    }
}
