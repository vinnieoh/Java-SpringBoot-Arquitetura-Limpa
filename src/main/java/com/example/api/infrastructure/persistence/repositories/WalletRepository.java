package com.example.api.infrastructure.persistence.repositories;

import com.example.api.infrastructure.persistence.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletModel, UUID> {
    Optional<WalletModel> findByUserUserId(UUID userId);
}