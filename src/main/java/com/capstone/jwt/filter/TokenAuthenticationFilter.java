package com.capstone.jwt.filter;

import com.capstone.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 오탈자 수정: "Authorization"
        String HEADER = "Authorization";
        String authorization = request.getHeader(HEADER);

        // 올바른 변수 사용
        String token = getAccessToken(authorization);

        try {
            if (token != null) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (IllegalArgumentException e) {
            throw new JwtException("Invalid Token. 유효하지 않은 토큰");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired Token. 토큰 기한 만료");
        } catch (SignatureException e) {
            throw new JwtException("Signature Failed. 인증 실패");
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        String PREFIX = "Bearer ";
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
            return authorizationHeader.substring(PREFIX.length());
        } else {
            return null;
        }
    }
}
