package com.sparta.memo.controller;

import com.sparta.memo.dto.CommentRequestDto;
import com.sparta.memo.dto.CommentResponseDto;
import com.sparta.memo.entity.Comment;
import com.sparta.memo.entity.User;
import com.sparta.memo.service.CommentService;
import com.sparta.memo.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return commentService.createComment(requestDto, user.getUsername());
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody Map<String,String> contents, HttpServletRequest req) {

        User user = (User) req.getAttribute("user");

        return commentService.updateComment(id, contents.get("contents"), user.getUsername(), user.getRole());
    }

//        @DeleteMapping("/comment/{id}")
//        public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest req) {
//            User user = (User) req.getAttribute("user");
//
//            return memoService.deleteMemo(id, user.getUsername(), user.getRole());
//        }
}