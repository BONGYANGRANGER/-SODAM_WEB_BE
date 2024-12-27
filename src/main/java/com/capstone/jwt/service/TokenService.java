package com.capstone.jwt.service;

import com.capstone.jwt.TokenProvider;
import com.capstone.jwt.db.RefreshTokenRepository;
import com.capstone.jwt.model.*;
import com.capstone.user.model.User;
import com.capstone.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessTokenResponseDto getAccessToken(AccessTokenRequestDto request){
        User user = userService.getUser(request.getEmail());
        if(user != null) {
            if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
                return createAccessToken(user, null);
            }
        }
        return null;
    }

    private AccessTokenResponseDto createAccessToken(User user, String refreshToken) {
        Duration tokenDuration = Duration.ofMinutes(jwtProperties.getDuration());
        Duration refreshDuration = Duration.ofMinutes(jwtProperties.getRefreshDuration());

// refreshToken 검색
        RefreshToken savedRefreshToken = refreshTokenRepository.findByEmail(user.getEmail()).orElse(null);

        if (savedRefreshToken != null && refreshToken != null) {
// 전달 받은 리프레시 토큰이 사용자에게 저장된 토큰과 다르다면
            if (!savedRefreshToken.getRefreshToken().equals(refreshToken))
                return new AccessTokenResponseDto("Invalid token.", null, null);
        }

        String accessToken = tokenProvider.generateToken(user, tokenDuration, true);
        String newRefreshToken = tokenProvider.generateToken(user, refreshDuration, false);

        if (savedRefreshToken == null)
            savedRefreshToken = new RefreshToken(user.getUsername(), newRefreshToken);
        else
            savedRefreshToken.setRefreshToken(newRefreshToken);
        refreshTokenRepository.save(savedRefreshToken);
        return new AccessTokenResponseDto("ok", accessToken, newRefreshToken);
    }

    public AccessTokenResponseDto refreshAccessToken(CreateAccessTokenByRefreshTokenDto request){
        try {
            Claims claims = tokenProvider.getClaims(request.getRefreshToken());
            String type = claims.get("type").toString();
            if (type == null || !type.equals("R")) {
                throw new Exception("Invalid token");
            }

            User user = userService.getUser(claims.getSubject());
            return createAccessToken(user, request.getRefreshToken());

        } catch(ExpiredJwtException e){
            return new AccessTokenResponseDto("만료된 토큰", null, null);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new AccessTokenResponseDto(e.getMessage(), null, null);
        }
    }
}
