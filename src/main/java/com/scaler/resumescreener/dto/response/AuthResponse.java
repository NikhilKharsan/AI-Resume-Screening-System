package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String tokenType;
    private UserResponseDto user;
}