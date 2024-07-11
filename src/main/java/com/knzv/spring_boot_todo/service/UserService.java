package com.knzv.spring_boot_todo.service;

import com.knzv.spring_boot_todo.dto.RegistrationRequest;
import com.knzv.spring_boot_todo.exception.UserAlreadyExistsException;
import com.knzv.spring_boot_todo.model.User;
import com.knzv.spring_boot_todo.model.UserRole;
import com.knzv.spring_boot_todo.repository.UserRepository;
import com.knzv.spring_boot_todo.security.JwtUtilities;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(RegistrationRequest request) throws UserAlreadyExistsException{
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
        return user;
    }

    public String login(RegistrationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким именем не найден"));

        return jwtUtilities.generateAccessToken(user);
    }

}
