package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private  final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto responseDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(responseDto);

        // Memo의 Max ID 찾기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1: 1;
        memo.setId(maxId);

        // DB저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos") // 조회 API
    public List<MemoResponseDto> getMemos() {
        // Map -> List
        List<MemoResponseDto> responseList = memoList.values().stream() // stream은 for문의 역할
                .map(MemoResponseDto::new).toList();

        return responseList;
    }

}
