package com.lvho.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lvho.invoice.data.request.LoginRequest;
import com.lvho.invoice.data.request.UserInfoRequest;
import com.lvho.invoice.data.response.LoginResponse;
import com.lvho.invoice.data.response.UserInfoResponse;
import com.lvho.invoice.service.UserInfoService;
import com.lvho.invoice.utils.Mapper;

@RestController
@RequestMapping("/")
public class UserController {
 
    @Autowired
    private UserInfoService service; 

    @Autowired
    private Mapper mapper;
  
    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(@RequestBody UserInfoRequest userInfoRequest) { 
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToUserInfoResponse(service.create(userInfoRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) { 
        return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
    }

    // @GetMapping("/profile")
    // public ResponseEntity<UserInfoResponse> getOwnProfile
}