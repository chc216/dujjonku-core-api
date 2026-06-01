package com.example.core.adminLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.core.adminLogin.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByLoginId(String loginId);
}
