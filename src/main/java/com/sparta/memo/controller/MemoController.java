package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto);
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    @GetMapping("/memos/contents")
    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        return memoService.getMemosByKeyword(keyword);
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        String password = requestDto.getPassword();
        return memoService.updateMemo(id, requestDto, password);
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        return memoService.deleteMemo(id, password);
    }
}