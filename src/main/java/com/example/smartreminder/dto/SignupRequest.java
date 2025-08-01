package com.example.smartreminder.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
} 