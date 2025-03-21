package com.example.api.adapters.controllers;


import com.example.api.adapters.dto.CreateUserDto;
import com.example.api.application.usecases.CreateUserUseCase;
import com.example.api.domain.entities.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static com.example.api.adapters.controllers.ApiVersion.V1;


@RestController
@RequestMapping(V1 + "/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        User user = new User(
                null,
                dto.fullName(),
                dto.document(),
                dto.email(),
                dto.password(),
                dto.userType()
        );

        createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
