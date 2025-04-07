package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    
    public ResponseEntity<?> register(RegisterRequest request) {
        

        return  ResponseEntity.ok("User Resgistered");
    }

    public ResponseEntity<?> login(LoginRequest request) {
        return ResponseEntity.ok(new AuthResponse("jwt-token"));
    }

    public ResponseEntity<?> getUserRole(HttpServletRequest request){
        return ResponseEntity.ok("USER_ROLE");
    }
}
