package com.example.smartreminder.service;

import com.example.smartreminder.model.Todo;
import com.example.smartreminder.repository.TodoRepository;
import com.example.smartreminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Todo> getTodosByUser(String username) {
        Long userId = userRepository.findByUsername(username).get().getId();
        return todoRepository.findByUserId(userId);
    }

    public Todo createTodo(Todo todo, String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            todo.setUser(user);
            todo.setCreatedAt(LocalDateTime.now());
            todo.setCompleted(false);
        });
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setTitle(todoDetails.getTitle());
        todo.setContent(todoDetails.getContent());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setDueAt(todoDetails.getDueAt());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
} 