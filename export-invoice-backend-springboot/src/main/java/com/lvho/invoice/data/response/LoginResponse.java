package com.lvho.invoice.data.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse { 

    private String token;
    private Date expiredAt;
}