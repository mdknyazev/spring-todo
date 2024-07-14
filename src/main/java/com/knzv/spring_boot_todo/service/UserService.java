package com.knzv.spring_boot_todo.service;


import com.knzv.spring_boot_todo.dto.TokenResponse;
import com.knzv.spring_boot_todo.dto.UserRequest;
import com.knzv.spring_boot_todo.dto.UserResponse;
import com.knzv.spring_boot_todo.exception.UserAlreadyExistsException;
import com.knzv.spring_boot_todo.model.User;
import com.knzv.spring_boot_todo.model.UserRole;
import com.knzv.spring_boot_todo.repository.UserRepository;
import com.knzv.spring_boot_todo.security.JwtUtilities;
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

    public UserResponse register(UserRequest request) throws UserAlreadyExistsException{
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        return response;
    }

    public TokenResponse login(UserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким именем не найден"));

        TokenResponse response = TokenResponse.builder()
                .accessToken(jwtUtilities.generateAccessToken(user))
                .build();
        return response;
    }

}
