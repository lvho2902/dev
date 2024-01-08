package com.lvho.invoice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;

import com.lvho.invoice.auth.data.LoginRequest;
import com.lvho.invoice.auth.data.LoginResponse;
import com.lvho.invoice.auth.dto.UserInfoDto;
import com.lvho.invoice.auth.entity.UserInfo;
import com.lvho.invoice.auth.service.UserInfoService;
import com.lvho.invoice.utils.Mapper;

@RestController
@RequestMapping("/")
public class UserController {
 
    @Autowired
    private UserInfoService service; 

    @Autowired
    private Mapper mapper;
  
    @PostMapping("/register")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserInfoDto> register(@RequestBody UserInfo userInfo) { 
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToDto(service.create(userInfo)));
    }
  
    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
    public String userProfile() { 
        return "User Profile";
    }

    @PostMapping("/token")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody LoginRequest request) { 
        return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
    }
}