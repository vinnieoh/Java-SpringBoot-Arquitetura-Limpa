package com.example.api.infrastructure.persistence.repositories;

import com.example.api.infrastructure.persistence.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
}
