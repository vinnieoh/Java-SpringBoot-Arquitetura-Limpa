package com.example.api.adapters.dto.userDTO;

import com.example.api.domain.Enum.UserType;


public record UserResponseDto(
        String fullName,
        String email,
        String document,
        UserType userType
) {}