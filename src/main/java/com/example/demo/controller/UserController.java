package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.security.JwtService;
import com.example.demo.security.MyUserDetails;
import com.example.demo.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService service;
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User us){
        try{
            service.register(us);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User us) {
        try {
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            us.getUsername(),
                            us.getPassword()));

           
            if (authentication.isAuthenticated()) {
                // Lấy thông tin Account từ đối tượng xác thực
                SecurityContextHolder.getContext().setAuthentication(authentication);
                MyUserDetails account = (MyUserDetails) authentication.getPrincipal();

                // Tạo JWT token
                String token = jwtService.generateToken(account.getUsername());
               
                // Trả về token và role
                return ResponseEntity.ok(
                        token
                        );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
