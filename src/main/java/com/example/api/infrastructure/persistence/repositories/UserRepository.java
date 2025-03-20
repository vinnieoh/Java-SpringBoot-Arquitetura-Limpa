package com.example.api.infrastructure.persistence.repositories;

import com.example.api.infrastructure.persistence.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByDocument(String document);
    Optional<UserModel> findByEmail(String email);
}