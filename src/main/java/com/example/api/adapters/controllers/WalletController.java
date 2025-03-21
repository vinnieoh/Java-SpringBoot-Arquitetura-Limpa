package com.example.api.adapters.controllers;


import com.example.api.domain.exceptions.BusinessException;
import com.example.api.application.usecases.wallet.WalletUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.api.infrastructure.config.configs.Configs.API_V1;

@RestController
@RequestMapping(API_V1 + "/wallet")
public class WalletController {

    private final WalletUseCase walletUseCase;

    public WalletController(WalletUseCase walletUseCase) {
        this.walletUseCase = walletUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getBalance(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getToken().getSubject());

        try {
            BigDecimal balance = walletUseCase.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam BigDecimal amount, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getToken().getSubject());

        try {
            walletUseCase.credit(userId, amount);
            return ResponseEntity.ok().body("Depósito de R$ " + amount + " realizado com sucesso!");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam BigDecimal amount, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getToken().getSubject());

        try {
            walletUseCase.debit(userId, amount);
            return ResponseEntity.ok().body("Saque de R$ " + amount + " realizado com sucesso!");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}