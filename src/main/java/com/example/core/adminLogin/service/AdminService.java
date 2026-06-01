package com.example.core.adminLogin.service;

import com.example.core.jwt.JwtProvider;
import com.example.core.adminLogin.repository.AdminRepository;
import com.example.core.adminLogin.dto.AdminRequest;
import com.example.core.adminLogin.domain.Admin;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    public String login(AdminRequest adminRequest){
        Admin admin = adminRepository.findByLoginId(adminRequest.getLoginId());

        if(admin == null){
            throw new IllegalArgumentException("존재하지 않는 관리자 아이디입니다.");
        }
        if(!admin.getPassword().equals(adminRequest.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtProvider.generateToken(adminRequest.getLoginId());
    }
}
