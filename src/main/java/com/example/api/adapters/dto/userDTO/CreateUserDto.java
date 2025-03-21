package com.example.api.adapters.dto.userDTO;

import com.example.api.domain.Enum.UserType;

public record CreateUserDto(
        String fullName,
        String document,
        String email,
        String password,
        UserType userType
) {
}