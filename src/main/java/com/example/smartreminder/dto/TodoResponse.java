package com.example.smartreminder.dto;

import com.example.smartreminder.model.TodoStatus;
import com.example.smartreminder.model.Urgency;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String content;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime dueAt;
    private LocalDateTime reminderAt;
    private Urgency urgency;

    public void determineStatus() {
        if (this.status != TodoStatus.COMPLETED) {
            if (this.dueAt != null && LocalDateTime.now().isAfter(this.dueAt)) {
                this.status = TodoStatus.EXPIRED;
            } else {
                this.status = TodoStatus.IN_PROGRESS;
            }
        }
    }
}
