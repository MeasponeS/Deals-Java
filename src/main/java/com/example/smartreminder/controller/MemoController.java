package com.example.smartreminder.controller;

import com.example.smartreminder.dto.MemoDto;
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
    public List<MemoDto> getAllMemos(Authentication authentication,
                                     @RequestParam(required = false) String title) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memoService.getMemos(userDetails.getUsername(), title);
    }

    @PostMapping
    public MemoDto createMemo(@RequestBody MemoDto memoDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memoService.createMemo(memoDto, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoDto> updateMemo(@PathVariable(value = "id") Long memoId,
                                              @RequestBody MemoDto memoDto) {
        final MemoDto updatedMemo = memoService.updateMemo(memoId, memoDto);
        return ResponseEntity.ok(updatedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemo(@PathVariable(value = "id") Long memoId) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.ok().build();
    }
}
