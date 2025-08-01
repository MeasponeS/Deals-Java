package com.example.smartreminder.dto;

import com.example.smartreminder.model.Urgency;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoRequest {
    private String title;
    private String content;
    private LocalDateTime dueAt;
    private LocalDateTime reminderAt;
    private Urgency urgency;
}
