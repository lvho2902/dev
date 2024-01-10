// package com.lvho.invoice.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; 
// import org.springframework.web.bind.annotation.*;

// import com.lvho.invoice.data.LoginRequest;
// import com.lvho.invoice.data.LoginResponse;
// import com.lvho.invoice.dto.UserInfoDto;
// import com.lvho.invoice.entity.UserInfo;
// import com.lvho.invoice.service.UserInfoService;
// import com.lvho.invoice.utils.Mapper;

// @RestController
// @RequestMapping("/")
// public class UserController {
 
//     @Autowired
//     private UserInfoService service; 

//     @Autowired
//     private Mapper mapper;
  
//     @PostMapping("/register")
//     // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//     public ResponseEntity<UserInfoDto> register(@RequestBody UserInfo userInfo) { 
//         return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToDto(service.create(userInfo)));
//     }
  
//     @GetMapping("/user/profile")
//     @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
//     public String userProfile(Authentication authentication) { 
//         return authentication.getUsername();
//     }

//     @PostMapping("/token")
//     public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody LoginRequest request) { 
//         return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
//     }
// }