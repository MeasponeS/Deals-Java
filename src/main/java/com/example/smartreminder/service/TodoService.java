package com.example.smartreminder.service;

import com.example.smartreminder.dto.TodoGroupByDateResponse;
import com.example.smartreminder.dto.TodoRequest;
import com.example.smartreminder.dto.TodoResponse;
import com.example.smartreminder.model.Todo;
import com.example.smartreminder.model.TodoStatus;
import com.example.smartreminder.repository.TodoRepository;
import com.example.smartreminder.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TodoResponse> getTodos(String username, String title, TodoStatus status) {
        List<Todo> todos;
        if (title != null && status != null) {
            todos = todoRepository.findByUser_UsernameAndTitleContainingAndStatus(username, title, status);
        } else if (title != null) {
            todos = todoRepository.findByUser_UsernameAndTitleContaining(username, title);
        } else if (status != null) {
            todos = todoRepository.findByUser_UsernameAndStatus(username, status);
        } else {
            todos = todoRepository.findByUser_Username(username);
        }

        return todos.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TodoGroupByDateResponse> getTodosGroupedByDate(String username) {
        List<Todo> todos = todoRepository.findByUser_Username(username);
        Map<LocalDate, List<TodoResponse>> groupedTodos = todos.stream()
                .filter(todo -> todo.getDueAt() != null)
                .map(this::convertToResponse)
                .collect(Collectors.groupingBy(todo -> todo.getDueAt().toLocalDate()));

        return groupedTodos.entrySet().stream()
                .map(entry -> new TodoGroupByDateResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public TodoResponse createTodo(TodoRequest todoRequest, String username) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoRequest, todo);
        userRepository.findByUsername(username).ifPresent(todo::setUser);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setStatus(TodoStatus.IN_PROGRESS);
        Todo savedTodo = todoRepository.save(todo);
        return convertToResponse(savedTodo);
    }

    public TodoResponse updateTodo(Long id, TodoRequest todoRequest) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        BeanUtils.copyProperties(todoRequest, todo, "id", "user", "createdAt", "status");
        Todo updatedTodo = todoRepository.save(todo);
        return convertToResponse(updatedTodo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoResponse markAsCompleted(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setStatus(TodoStatus.COMPLETED);
        Todo updatedTodo = todoRepository.save(todo);
        return convertToResponse(updatedTodo);
    }

    private TodoResponse convertToResponse(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        BeanUtils.copyProperties(todo, todoResponse);
        todoResponse.determineStatus();
        return todoResponse;
    }
}
