package com.lvho.invoice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import com.lvho.invoice.custom.exception.CustomException;
import com.lvho.invoice.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = userInfoService.resolveToken(httpServletRequest);
        try {
                if(token == null) throw new CustomException("aa", HttpStatus.UNAUTHORIZED);
                String username = userInfoService.extractUsername(token);
                UserDetails userDetails = userInfoService.loadUserByUsername(username); 
                if (userInfoService.validateToken(token, userDetails)) {
                    Authentication auth = userInfoService.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
        } 
        catch (CustomException ex) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
} 