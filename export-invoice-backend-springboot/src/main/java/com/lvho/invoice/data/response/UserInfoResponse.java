package com.lvho.invoice.data.response;

import java.util.List;

import com.lvho.invoice.entity.Role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserInfoResponse {

    private String id; 
    private String username;
    private String email;
    List<Role> roles;
}
