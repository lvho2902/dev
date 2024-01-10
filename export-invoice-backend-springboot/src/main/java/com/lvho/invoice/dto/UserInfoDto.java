package com.lvho.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoDto {
    private String id; 
    private String userName;
    private String email;
    private String roles;
}
