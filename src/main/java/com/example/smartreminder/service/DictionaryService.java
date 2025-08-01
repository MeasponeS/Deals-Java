package com.example.smartreminder.service;

import com.example.smartreminder.model.Dictionary;
import com.example.smartreminder.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public List<Dictionary> getDictionaries(String type) {
        if (type != null && !type.isEmpty()) {
            return dictionaryRepository.findByType(type);
        } else {
            return dictionaryRepository.findAll();
        }
    }
}
