package com.example.smartreminder.controller;

import com.example.smartreminder.model.Memo;
import com.example.smartreminder.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    @Autowired
    private MemoService memoService;

    @GetMapping
    public List<Memo> getAllMemos(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memoService.getMemosByUser(userDetails.getUsername());
    }

    @PostMapping
    public Memo createMemo(@RequestBody Memo memo, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memoService.createMemo(memo, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable(value = "id") Long memoId,
                                           @RequestBody Memo memoDetails) {
        final Memo updatedMemo = memoService.updateMemo(memoId, memoDetails);
        return ResponseEntity.ok(updatedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemo(@PathVariable(value = "id") Long memoId) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.ok().build();
    }
} 