package com.sparta.memo.controller;

import com.sparta.memo.dto.LoginDto;
import com.sparta.memo.dto.SignUpDto;
import com.sparta.memo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 그냥 컨트롤러라 return값 뒤에 html 붙음
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDto requestDto) {
        userService.signup(requestDto);
        String jsonResponse = "{\"msg\": \"회원가입 성공\", \"statusCode\": 200}";
        return ResponseEntity.ok(jsonResponse);

    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginDto requestDto, HttpServletResponse res) {
        String jsonResponse = "{\"msg\": \"로그인 성공\", \"statusCode\": 200}";
        userService.login(requestDto, res);
        return ResponseEntity.ok(jsonResponse);
    }
}
