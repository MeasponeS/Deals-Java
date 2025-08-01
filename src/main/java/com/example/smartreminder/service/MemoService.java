package com.example.smartreminder.service;

import com.example.smartreminder.dto.MemoDto;
import com.example.smartreminder.model.Memo;
import com.example.smartreminder.repository.MemoRepository;
import com.example.smartreminder.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MemoDto> getMemos(String username, String title) {
        List<Memo> memos;
        if (title != null && !title.isEmpty()) {
            memos = memoRepository.findByUser_UsernameAndTitleContaining(username, title);
        } else {
            memos = memoRepository.findByUser_Username(username);
        }
        return memos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MemoDto createMemo(MemoDto memoDto, String username) {
        Memo memo = new Memo();
        BeanUtils.copyProperties(memoDto, memo, "id", "createdAt", "updatedAt");
        userRepository.findByUsername(username).ifPresent(memo::setUser);
        memo.setCreatedAt(LocalDateTime.now());
        memo.setUpdatedAt(LocalDateTime.now());
        Memo savedMemo = memoRepository.save(memo);
        return convertToDto(savedMemo);
    }

    public MemoDto updateMemo(Long id, MemoDto memoDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new RuntimeException("Memo not found"));
        BeanUtils.copyProperties(memoDto, memo, "id", "user", "createdAt", "updatedAt");
        memo.setUpdatedAt(LocalDateTime.now());
        Memo updatedMemo = memoRepository.save(memo);
        return convertToDto(updatedMemo);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }

    private MemoDto convertToDto(Memo memo) {
        MemoDto memoDto = new MemoDto();
        BeanUtils.copyProperties(memo, memoDto);
        return memoDto;
    }
}
