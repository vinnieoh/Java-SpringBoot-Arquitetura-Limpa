package com.example.api.adapters.controllers;


import com.example.api.adapters.dto.userDTO.CreateUserDto;
import com.example.api.adapters.dto.errorsDTO.ErrorResponse;
import com.example.api.adapters.dto.userDTO.UpdateUserDto;
import com.example.api.application.usecases.CreateUserUseCase;
import com.example.api.application.usecases.DeleteUserUseCase;
import com.example.api.application.usecases.GetUserByIdUseCase;
import com.example.api.application.usecases.UpdateUserUseCase;
import com.example.api.domain.entities.User;

import com.example.api.domain.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.api.adapters.controllers.ApiVersion.V1;


@RestController
@RequestMapping(V1 + "/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserByIdUseCase getUserByIdUseCase,
                          DeleteUserUseCase deleteUserUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
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

        } catch (UserException e) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new ErrorResponse(e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno inesperado."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserById(JwtAuthenticationToken token) {
        try {
            var user = getUserByIdUseCase.execute(token);
            return ResponseEntity.ok(user);

        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado."));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto dto, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getToken().getSubject());

        try {
            updateUserUseCase.execute(userId, dto);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getToken().getSubject());

        try {
            deleteUserUseCase.execute(userId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }



}
