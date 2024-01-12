package com.lvho.invoice.data.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequest {
    private String id; 
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
