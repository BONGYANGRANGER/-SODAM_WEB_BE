package com.capstone.config;

import com.capstone.jwt.filter.TokenAuthenticationFilter;
import com.capstone.jwt.filter.TokenExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * - Spring Boot 3.x / Spring Security 6.x 기준
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenExceptionFilter tokenExceptionFilter;

    /**
     * SecurityFilterChain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**",
                                "/api/users/login/**", "/api/users/signup"
                        ).permitAll() // 특정 경로 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )

                // 세션 사용 안 함 (JWT 토큰 사용)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 필터 추가
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenExceptionFilter, TokenAuthenticationFilter.class);

        return http.build();
    }

    /**
     * BCryptPasswordEncoder 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
