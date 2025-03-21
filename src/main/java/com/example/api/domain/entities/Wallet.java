package com.example.api.domain.entities;


import com.example.api.domain.exceptions.BusinessException;
import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {

    private final UUID walletId;
    private final UUID userId;
    private BigDecimal balance;

    public Wallet(UUID walletId, UUID userId, BigDecimal balance) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new BusinessException("Saldo insuficiente para a operação.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}

