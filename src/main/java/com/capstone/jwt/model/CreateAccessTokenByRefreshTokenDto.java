package com.capstone.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenByRefreshTokenDto {
    private String refreshToken;
}
