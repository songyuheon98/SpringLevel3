package com.sparta.memo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.memo.entity.Memo;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MemoResponseDto { // 응답하는 Dto
    // @JsonIgnore는 id, pw 노출을 피하기 위함
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String user_pw;
    private String title;
    private String username;
    private String contents;
    private Timestamp updatedAt;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.user_pw = memo.getUser_pw();
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.updatedAt = memo.getUpdatedAt();
    }

    public MemoResponseDto(Long id, String user_pw, String title, String username, String contents, Timestamp updatedAt) {
        this.id = id;
        this.user_pw = user_pw;
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.updatedAt = updatedAt;
    }

    public MemoResponseDto(String title, String username, String contents, Timestamp updatedAt) { // 2번(전체 게시글 관련)
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.updatedAt = updatedAt;
    }
}
