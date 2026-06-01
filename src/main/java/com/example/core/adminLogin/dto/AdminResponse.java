package com.example.core.adminLogin.dto;

import lombok.Getter;
import lombok.Builder;

@Builder
@Getter
public class AdminResponse {
    private String token;
}
