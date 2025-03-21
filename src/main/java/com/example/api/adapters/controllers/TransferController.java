package com.example.api.adapters.controllers;

import com.example.api.adapters.dto.transferDTO.TransferRequestDto;
import com.example.api.adapters.dto.errorsDTO.ErrorResponse;
import com.example.api.application.usecases.transactions.TransferMoneyUseCase;
import com.example.api.domain.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.api.infrastructure.config.configs.Configs.API_V1;

@RestController
@RequestMapping(API_V1 + "/transfer")
public class TransferController {

    private final TransferMoneyUseCase transferUseCase;

    public TransferController(TransferMoneyUseCase transferUseCase) {
        this.transferUseCase = transferUseCase;
    }


    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequestDto dto, JwtAuthenticationToken token) {

        UUID payerId = UUID.fromString(token.getToken().getSubject());

        try {
            transferUseCase.execute(payerId, dto);
            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro inesperado ao processar a transferência."));
        }
    }

}
