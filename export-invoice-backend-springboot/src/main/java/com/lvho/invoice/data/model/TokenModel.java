package com.lvho.invoice.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class TokenModel {
    private String accessToken;
    private String refreshToken;
}
