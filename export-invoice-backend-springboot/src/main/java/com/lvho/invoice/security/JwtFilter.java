package com.lvho.invoice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvho.invoice.custom.exception.CustomException;
import com.lvho.invoice.service.UserInfoService;
import com.lvho.invoice.utils.Constants;
import com.lvho.invoice.data.response.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String[] ignoreCsrfAntMatchers = {
        "/login", "/employee", "/project", "/customer",
        "/employee/**", "/project/**", "/customer/**" 
    };

    public String[] getIgnoreCsrfAntMatchers(){
        return this.ignoreCsrfAntMatchers;
    }

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = userInfoService.resolveToken(httpServletRequest);
        try {
            if(isPyPassToken(httpServletRequest)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            if(token == null) throw new CustomException(Constants.MESSAGE_BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
            
            String username = userInfoService.extractUsername(token);
            UserDetails userDetails = userInfoService.loadUserByUsername(username); 
            if (userInfoService.validateToken(token, userDetails)) {
                Authentication auth = userInfoService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (CustomException ex) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(httpServletResponse, ex);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isPyPassToken(HttpServletRequest httpServletRequest){
        // int a = Arrays.binarySearch(getIgnoreCsrfAntMatchers(), httpServletRequest.getServletPath());
        // return a >= 0;
        return true;
    }

    private void sendErrorResponse(HttpServletResponse httpServletResponse, CustomException ex) throws IOException {
        httpServletResponse.setStatus(ex.getHttpStatus().value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        String errorResponseJson = new ObjectMapper().writeValueAsString(errorResponse);
        httpServletResponse.getWriter().write(errorResponseJson);

        httpServletResponse.getWriter().flush();
    }
} 