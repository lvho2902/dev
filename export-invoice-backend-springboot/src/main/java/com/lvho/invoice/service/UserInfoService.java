package com.lvho.invoice.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lvho.invoice.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.custom.exception.CustomException;
import com.lvho.invoice.data.model.TokenModel;
import com.lvho.invoice.data.request.LoginRequest;
import com.lvho.invoice.data.request.UserInfoRequest;
import com.lvho.invoice.entity.Role;
import com.lvho.invoice.entity.UserInfo;
import com.lvho.invoice.repository.UserInfoRepository;
import com.lvho.invoice.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserInfoRepository userRepo;

	@Lazy
	@Autowired
    private AuthenticationManager authenticationManager; 

	@Autowired
    private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		UserInfo user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		UserDetails userDetails = new User(
			user.getUsername(),
			user.getPassword(),
			user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toList())
		);

    	return userDetails;
	} 

	public UserInfo create(UserInfoRequest userInfoRequest) { 
        if(userInfoRequest.getUsername() == null || userInfoRequest.getUsername().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_USER_NAME);
        if(userInfoRequest.getEmail() == null || userInfoRequest.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(userRepo.existsByUsername(userInfoRequest.getUsername())) throw new BadRequestException(Constants.MESSAGE_SAME_USER_NAME_EXIST);
		if(userRepo.existsByEmail(userInfoRequest.getEmail())) throw new BadRequestException(Constants.MESSAGE_SAME_USER_EMAIL_EXIST);

		if(userInfoRequest.getRoles() == null || userInfoRequest.getRoles().isEmpty() || !Role.isValidRoles(userInfoRequest.getRoles()))
			throw new BadRequestException(Constants.MESSAGE_INVALID_ROLES);

		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(userInfoRequest.getUsername());
		userInfo.setPassword(encoder.encode(userInfoRequest.getPassword()));
		userInfo.setEmail(userInfoRequest.getEmail());
		userInfo.setRoles(userInfoRequest.getRoles());

		return userRepo.save(userInfo);
	}

	public TokenModel login(LoginRequest request){
		if(request.getUsername() == null || request.getUsername().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_USER_NAME);
		if(request.getPassword() == null || request.getPassword().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_PASSWORD);

		try {
			UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			authenticationManager.authenticate(userAuth);
			UserInfo userInfo = userRepo.findByUsername(request.getUsername());
			String accessToken = jwtProvider.generateToken(request.getUsername(), userInfo.getRoles(), Constants.ACCESS_TOKEN_EXPIRATION_MINUTES);
			String refreshToken = jwtProvider.generateToken(request.getUsername(), new ArrayList<>(), Constants.REFRESH_TOKEN_EXPIRATION_MINUTES);
			updateRefreshToken(userInfo, refreshToken);
			return new TokenModel(accessToken, refreshToken);
		}
		catch (AuthenticationException e) {
			throw new CustomException(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	public TokenModel refreshToken(TokenModel request){
		if(request.getAccessToken() == null || request.getAccessToken().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_ACCESS_TOKEN);
		if(request.getRefreshToken() == null || request.getRefreshToken().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_REFRESH_TOKEN);

		UserInfo userInfo = userRepo.findByUsername(extractUsername(request.getRefreshToken()));
		if(userInfo.getRefreshToken().compareTo(request.getRefreshToken()) != 0) throw new BadRequestException(Constants.MESSAGE_INVALID_REFRESH_TOKEN);

		String accessToken = jwtProvider.generateToken(userInfo.getUsername(), userInfo.getRoles(), Constants.ACCESS_TOKEN_EXPIRATION_MINUTES);
		return new TokenModel(accessToken, userInfo.getRefreshToken());
	}

	public Boolean validateToken(String token, UserDetails userDetails){
		return this.jwtProvider.validateToken(token, userDetails);
	}

	public String extractUsername(String token){ 
		return this.jwtProvider.extractUsername(token); 
	} 

	public String resolveToken(HttpServletRequest req){
		return this.jwtProvider.resolveToken(req);
	}

	public Authentication getAuthentication(String token){
		return jwtProvider.getAuthentication(token);
	}

	private void updateRefreshToken(UserInfo user, String refreshToken) {
		user.setRefreshToken(refreshToken);
		userRepo.save(user);
	}
} 

