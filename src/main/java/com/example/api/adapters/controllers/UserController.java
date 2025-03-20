package com.example.api.adapters.controllers;


import com.example.api.adapters.dto.CreateUserDto;
import com.example.api.infrastructure.persistence.model.UserModel;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        // Verifica se o e-mail ou o documento já existem no banco de dados
        if (userRepository.findByEmail(dto.email()).isPresent() ||
                userRepository.findByDocument(dto.document()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Email or Document already exists");
        }

        // Criando o usuário com os dados corretos e senha criptografada
        var user = new UserModel();
        user.setFullName(dto.fullName()); // Nome completo
        user.setDocument(dto.document()); // CPF/CNPJ correto
        user.setEmail(dto.email()); // E-mail correto
        user.setPassword(passwordEncoder.encode(dto.password())); // Senha criptografada
        user.setUserType(dto.userType()); // Tipo de usuário (COMMON ou MERCHANT)

        // Salvando no banco
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
