package com.example.smartreminder.controller;

import com.example.smartreminder.dto.TodoGroupByDateResponse;
import com.example.smartreminder.dto.TodoRequest;
import com.example.smartreminder.dto.TodoResponse;
import com.example.smartreminder.model.TodoStatus;
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
    public List<TodoResponse> getAllTodos(Authentication authentication,
                                          @RequestParam(required = false) String title,
                                          @RequestParam(required = false) TodoStatus status) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return todoService.getTodos(userDetails.getUsername(), title, status);
    }

    @GetMapping("/by-date")
    public List<TodoGroupByDateResponse> getTodosGroupedByDate(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return todoService.getTodosGroupedByDate(userDetails.getUsername());
    }

    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoRequest todoRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return todoService.createTodo(todoRequest, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable(value = "id") Long todoId,
                                                   @RequestBody TodoRequest todoRequest) {
        final TodoResponse updatedTodo = todoService.updateTodo(todoId, todoRequest);
        return ResponseEntity.ok(updatedTodo);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoResponse> markAsCompleted(@PathVariable(value = "id") Long todoId) {
        final TodoResponse updatedTodo = todoService.markAsCompleted(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }
}
