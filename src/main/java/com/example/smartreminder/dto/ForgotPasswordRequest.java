package com.example.smartreminder.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String username;
    private String securityQuestion;
    private String securityAnswer;
} 