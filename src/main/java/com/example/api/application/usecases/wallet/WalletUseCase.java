package com.example.api.application.usecases.wallet;

import com.example.api.domain.exceptions.BusinessException;
import com.example.api.infrastructure.persistence.model.WalletModel;
import com.example.api.infrastructure.persistence.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletUseCase {

    private final WalletRepository walletRepository;

    public WalletUseCase(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public BigDecimal getBalance(UUID userId) {
        return walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new BusinessException("Carteira não encontrada para o usuário."))
                .getBalance();
    }

    public void credit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O valor de depósito deve ser maior que zero.");
        }

        WalletModel wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new BusinessException("Carteira não encontrada para o usuário."));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }

    public void debit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O valor de saque deve ser maior que zero.");
        }

        WalletModel wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new BusinessException("Carteira não encontrada para o usuário."));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Saldo insuficiente.");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
    }
}
