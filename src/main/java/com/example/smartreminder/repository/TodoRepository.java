package com.example.smartreminder.repository;

import com.example.smartreminder.model.Todo;
import com.example.smartreminder.model.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser_Username(String username);
    List<Todo> findByUser_UsernameAndTitleContaining(String username, String title);
    List<Todo> findByUser_UsernameAndStatus(String username, TodoStatus status);
    List<Todo> findByUser_UsernameAndTitleContainingAndStatus(String username, String title, TodoStatus status);
}
