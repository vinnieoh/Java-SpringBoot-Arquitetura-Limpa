package com.example.api.adapters.dto.userDTO;

public record UpdateUserDto(
        String fullName,
        String email,
        String document,
        String password
) {}