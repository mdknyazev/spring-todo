package com.knzv.spring_boot_todo.controller;

import com.knzv.spring_boot_todo.dto.RegistrationRequest;
import com.knzv.spring_boot_todo.exception.UserAlreadyExistsException;
import com.knzv.spring_boot_todo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            var user = userService.register(request);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegistrationRequest request) {
        String accessToken = userService.login(request);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
