package com.example.smartreminder.repository;

import com.example.smartreminder.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    List<Dictionary> findByType(String type);
}
