package com.sparta.memo.dto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Long postid;
    private String contents;
}