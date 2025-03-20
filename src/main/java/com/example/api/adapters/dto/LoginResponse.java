package com.example.api.adapters.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}