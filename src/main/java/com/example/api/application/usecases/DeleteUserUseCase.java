package com.example.api.application.usecases;

import com.example.api.domain.exceptions.UserException;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado."));

        userRepository.delete(user);
    }
}