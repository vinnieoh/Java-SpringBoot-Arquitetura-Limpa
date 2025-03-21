package com.example.api.infrastructure.persistence.model;

import com.example.api.adapters.dto.loginDTO.LoginRequest;
import com.example.api.domain.Enum.UserType;
import com.example.api.domain.exceptions.BusinessException;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue
    @Column(name = "user_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "document", unique = true, nullable = false, length = 18)
    private String document;

    @Column(name = "email", unique = true, nullable = false, length = 120)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WalletModel wallet;


    public UserModel() {

    }

    public UserModel(String document, String password) {
        this.document = document;
        this.password = password;
    }

    @PrePersist
    public void initializeWallet() {
        this.wallet = new WalletModel(this); // Cria automaticamente uma carteira ao cadastrar usuário
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

}

