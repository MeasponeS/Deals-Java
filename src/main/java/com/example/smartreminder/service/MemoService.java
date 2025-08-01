package com.example.smartreminder.service;

import com.example.smartreminder.model.Memo;
import com.example.smartreminder.repository.MemoRepository;
import com.example.smartreminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Memo> getMemosByUser(String username) {
        Long userId = userRepository.findByUsername(username).get().getId();
        return memoRepository.findByUserId(userId);
    }

    public Memo createMemo(Memo memo, String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            memo.setUser(user);
            memo.setCreatedAt(LocalDateTime.now());
            memo.setUpdatedAt(LocalDateTime.now());
        });
        return memoRepository.save(memo);
    }

    public Memo updateMemo(Long id, Memo memoDetails) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new RuntimeException("Memo not found"));
        memo.setTitle(memoDetails.getTitle());
        memo.setContent(memoDetails.getContent());
        memo.setUpdatedAt(LocalDateTime.now());
        return memoRepository.save(memo);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
} 