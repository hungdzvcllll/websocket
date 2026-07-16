package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository repo;
    public void register(User us){
        repo.save(new User(null,us.getUsername(),encoder.encode(us.getPassword()),null,null));
    }
    public User findCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        UserDetails u = (UserDetails) principal;
        String name = u.getUsername();
        return repo.findByUsername(name);
    }
    
}
