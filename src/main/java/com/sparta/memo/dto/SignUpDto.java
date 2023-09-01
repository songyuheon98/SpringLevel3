package com.sparta.memo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpDto {
    private String username;
    private String password;
    private Boolean isAdmin = false;
    private String adminToken = "";
}
