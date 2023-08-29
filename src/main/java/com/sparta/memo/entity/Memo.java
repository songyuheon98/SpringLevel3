package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;
    private String user_pw;
    private String title;
    private String username;
    private String contents;
    private Timestamp updatedAt;

    public Memo(MemoRequestDto responseDto) {
        this.user_pw = responseDto.getUser_pw();
        this.username = responseDto.getUsername();
        this.title = responseDto.getTitle();
        this.contents = responseDto.getContents();
        this.updatedAt = responseDto.getUpdatedAt();
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.updatedAt = requestDto.getUpdatedAt();
    }

}
