package com.capstone.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponseDto {
    private String result;
    private String token;
    private String refreshToken;
}
