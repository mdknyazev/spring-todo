package com.knzv.spring_boot_todo.controller;


import com.knzv.spring_boot_todo.dto.TokenResponse;
import com.knzv.spring_boot_todo.dto.UserRequest;
import com.knzv.spring_boot_todo.dto.UserResponse;
import com.knzv.spring_boot_todo.exception.UserAlreadyExistsException;
import com.knzv.spring_boot_todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        UserResponse response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        TokenResponse response = userService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
