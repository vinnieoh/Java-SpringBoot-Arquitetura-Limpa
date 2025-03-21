package com.example.api.domain.entities;


import com.example.api.domain.Enum.UserType;

import java.util.UUID;

public class User {
    private UUID id;
    private String fullName;
    private String document;
    private String email;
    private String password;
    private UserType userType;

    public User(UUID id, String fullName, String document, String email, String password, UserType userType) {
        this.id = id;
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}
