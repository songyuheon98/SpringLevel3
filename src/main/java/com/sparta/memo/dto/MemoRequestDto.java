package com.sparta.memo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MemoRequestDto { // 정보 주는 Dto
    private String username;
    private String contents;

}
