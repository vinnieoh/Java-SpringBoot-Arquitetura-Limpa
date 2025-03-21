package com.example.api.application.usecases.user;

import com.example.api.adapters.dto.userDTO.UpdateUserDto;
import com.example.api.domain.exceptions.UserException;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(UUID userId, UpdateUserDto dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado."));

        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setDocument(dto.document());

        if (dto.password() != null && !dto.password().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }


        userRepository.save(user);
    }
}