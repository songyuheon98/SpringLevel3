package com.sparta.memo.controller;

import com.sparta.memo.dto.UserDto;
import com.sparta.memo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 그냥 컨트롤러라 return값 뒤에 html 붙음
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(UserDto requestDto) {
        userService.signup(requestDto);
        return "redirect:/api/user/login-page";
    }

}
