package com.scaler.resumescreener.dto.response;

import com.scaler.resumescreener.models.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime createdAt;
}