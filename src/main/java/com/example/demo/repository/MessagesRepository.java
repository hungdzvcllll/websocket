package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Messages;
import com.example.demo.entity.User;

public interface MessagesRepository extends JpaRepository<Messages,Integer>{
    public ArrayList<Messages> findBySendFromAndSendTo(User sender,User receiver);
}
