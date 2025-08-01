package com.example.smartreminder.controller;

import com.example.smartreminder.model.Todo;
import com.example.smartreminder.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return todoService.getTodosByUser(userDetails.getUsername());
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return todoService.createTodo(todo, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable(value = "id") Long todoId,
                                           @RequestBody Todo todoDetails) {
        final Todo updatedTodo = todoService.updateTodo(todoId, todoDetails);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }
} 