package com.example.api.infrastructure.persistence.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_wallets")
public class WalletModel {

    @Id
    @GeneratedValue
    @Column(name = "wallet_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID walletId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    public WalletModel() {}

    public WalletModel(UserModel user) {
        this.user = user;
        this.balance = BigDecimal.ZERO;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UserModel getUser() {
        return user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
