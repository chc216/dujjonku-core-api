package com.example.core.adminLogin.controller;

import com.example.core.adminLogin.dto.AdminRequest;
import com.example.core.adminLogin.dto.AdminResponse;
import com.example.core.adminLogin.service.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AdminResponse> login(@RequestBody AdminRequest adminRequest) {
        AdminResponse response = AdminResponse.builder()
                .token(adminService.login(adminRequest))
                .build();

        return ResponseEntity.ok(response);
    }
}
