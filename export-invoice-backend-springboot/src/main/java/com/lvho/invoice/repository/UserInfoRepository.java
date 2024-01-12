package com.lvho.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.UserInfo; 
  
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> { 
    UserInfo findByUsername(String username); 
    UserInfo findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}