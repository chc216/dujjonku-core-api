package com.example.core.adminLogin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminRequest {
    private String loginId;
    private String password;
}