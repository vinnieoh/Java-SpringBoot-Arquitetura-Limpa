package com.example.api.domain.entities;


import com.example.api.domain.Enum.UserType;

public class User {
    private Long id;
    private String fullName;
    private String document;
    private String email;
    private String password;
    private UserType userType;

    public User(Long id, String fullName, String document, String email, String password, UserType userType) {
        this.id = id;
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public Long getId() {
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
