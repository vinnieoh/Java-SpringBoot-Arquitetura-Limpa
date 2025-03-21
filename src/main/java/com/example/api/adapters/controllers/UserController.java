package com.example.api.adapters.controllers;


import com.example.api.adapters.dto.CreateUserDto;
import com.example.api.adapters.dto.ErrorResponse;
import com.example.api.application.usecases.CreateUserUseCase;
import com.example.api.domain.entities.User;

import com.example.api.domain.exceptions.InvalidUserException;
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
    public ResponseEntity<Object> newUser(@RequestBody CreateUserDto dto) {
        try {
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

        } catch (InvalidUserException e) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new ErrorResponse(e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace(); // 👈 isso vai mostrar o que está acontecendo
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno inesperado."));
        }
    }


}
