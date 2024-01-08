package com.lvho.invoice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.auth.dto.UserInfoDto;
import com.lvho.invoice.auth.entity.UserInfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class Mapper {
    @Autowired
    private ModelMapper mapper;

    public UserInfoDto convertToDto(UserInfo user) {
        UserInfoDto dto = mapper.map(user, UserInfoDto.class);
        return dto; 
    }
}
