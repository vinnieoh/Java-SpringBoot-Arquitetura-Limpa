package com.example.api.application.usecases.user;

import com.example.api.adapters.dto.userDTO.UserResponseDto;
import com.example.api.domain.exceptions.UserException;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto execute(JwtAuthenticationToken token) {
        String userId = token.getToken().getSubject(); // o sub do JWT deve conter o UUID
        UUID id = UUID.fromString(userId);

        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("Usuário não encontrado."));

        return new UserResponseDto(
                user.getFullName(),
                user.getEmail(),
                user.getDocument(),
                user.getUserType()
        );
    }
}