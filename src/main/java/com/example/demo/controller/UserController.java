package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.serviceImpl.JwtService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws MessagingException {
        User userdetails=service.saveUser(user);
       return ResponseEntity.ok(userdetails);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto loginUser){
        UserDetails userDetails=userDetailsService.loadUserByUsername(loginUser.getEmail());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getEmail(),loginUser.getPassword())
            );
        }catch (AuthenticationException e){
            return ResponseEntity.status(401).body("Invalid username and password");
        }
        String jwtToken = jwtService.generateToken(userDetails.getUsername());

        // Return JWT token as response
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }
    @GetMapping("/getUser")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userdetails=service.findAllUser();
        return ResponseEntity.ok(userdetails);
    }
    static class JwtResponse {
        private final String jwtToken;

        public JwtResponse(String jwtToken) {
            this.jwtToken = jwtToken;
        }

        public String getJwtToken() {
            return jwtToken;
        }
    }
}
