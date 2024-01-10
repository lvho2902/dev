// package com.lvho.invoice.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails; 
// import org.springframework.security.core.userdetails.UserDetailsService; 
// import org.springframework.security.core.userdetails.UsernameNotFoundException; 
// import org.springframework.security.crypto.password.PasswordEncoder; 
// import org.springframework.stereotype.Service;

// import com.lvho.invoice.utils.Constants;
// import com.lvho.invoice.custom.exception.BadRequestException;
// import com.lvho.invoice.data.LoginRequest;
// import com.lvho.invoice.data.LoginResponse;
// import com.lvho.invoice.entity.UserInfo;
// import com.lvho.invoice.repository.UserInfoRepository;

// import java.util.Optional;

// @Service
// public class UserInfoService implements UserDetailsService { 

// 	@Autowired
// 	private UserInfoRepository userRepo; 

// 	@Lazy
// 	@Autowired
//     private AuthenticationManager authenticationManager; 

// 	@Autowired
//     private JwtService jwtService;

// 	@Autowired
// 	private PasswordEncoder encoder;

// 	@Override
// 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

// 		Optional<UserInfo> user = userRepo.findByUserName(username); 
// 		return user.map(UserInfoDetails::new) 
// 				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
// 	} 

// 	public UserInfo create(UserInfo userInfo) { 
//         if(userInfo.getUserName() == null || userInfo.getUserName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_USER_NAME);
//         if(userInfo.getEmail() == null || userInfo.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
//         if(userRepo.findByUserName(userInfo.getUserName()).isPresent()) throw new BadRequestException(Constants.MESSAGE_SAME_USER_NAME_EXIST);
// 		if(userRepo.findByEmail(userInfo.getEmail()).isPresent()) throw new BadRequestException(Constants.MESSAGE_SAME_USER_EMAIL_EXIST);

// 		userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
// 		return userRepo.save(userInfo); 
// 	}

// 	// public UserInfo getProfile(Authentication authentication){
// 	// 	string a = authentication.getName();
// 	// }

// 	public LoginResponse login(LoginRequest request){
// 		if(request.getUsername() == null || request.getUsername().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_USER_NAME);
// 		if(request.getPassword() == null || request.getPassword().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_PASSWORD);

// 		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())); 
//         if (authentication.isAuthenticated()) { 
// 			String token = jwtService.generateToken(request.getUsername());
//             return new LoginResponse(token, jwtService.extractExpiration(token));
//         }
// 		else throw new UsernameNotFoundException("aaa");
// 	}

// 	public Boolean validateToken(String token, UserDetails userDetails){
// 		return this.jwtService.validateToken(token, userDetails);
// 	}

// 	public String extractUsername(String token){ 
// 		return this.jwtService.extractUsername(token); 
// 	} 
// } 

