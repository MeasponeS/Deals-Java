package com.example.smartreminder.controller;

import com.example.smartreminder.model.Dictionary;
import com.example.smartreminder.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    public List<Dictionary> getDictionaries(@RequestParam(required = false) String type) {
        return dictionaryService.getDictionaries(type);
    }
}
