package com.lvho.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.UserInfo; 
  
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> { 
    Optional<UserInfo> findByUserName(String username); 
    Optional<UserInfo> findByEmail(String email);
}