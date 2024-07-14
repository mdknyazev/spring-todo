package com.knzv.spring_boot_todo.dto;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String accessToken;
}
