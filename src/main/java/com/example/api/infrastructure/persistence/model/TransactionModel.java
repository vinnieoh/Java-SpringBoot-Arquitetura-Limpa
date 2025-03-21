package com.example.api.infrastructure.persistence.model;


import com.example.api.domain.Enum.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transactions")
public class TransactionModel {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID transactionId;

    @Column(name = "payer_id", nullable = false)
    private UUID payerId;

    @Column(name = "payee_id", nullable = false)
    private UUID payeeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public TransactionModel() {
        this.createdAt = LocalDateTime.now();
    }

    public TransactionModel(UUID payerId, UUID payeeId, BigDecimal amount, TransactionStatus status) {
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.amount = amount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getPayerId() {
        return payerId;
    }

    public UUID getPayeeId() {
        return payeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
