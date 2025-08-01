package com.example.smartreminder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemoDto {
    private Long id;
    private String title;
    private String content;
    private String tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
