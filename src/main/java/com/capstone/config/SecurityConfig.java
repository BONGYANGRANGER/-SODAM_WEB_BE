package com.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 설정 클래스
 * - Spring Boot 3.x / Spring Security 6.x 기준
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityFilterChain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity 설정
        http
                // CSRF 비활성화(테스트/간단 API 시)
                .csrf(csrf -> csrf.disable())

                // 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/h2-console/**",
                                        "/**"   // 필요 시 특정 패턴만 permitAll로 변경
                                ).permitAll()
                                // 위의 매칭되는 경로는 인증 없이 접근 허용
                                .anyRequest().authenticated()
                        // 그 외 모든 요청은 인증 필요
                )

                // HTTP Basic or Form Login
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login.disable());
        // 폼 로그인 비활성화 (REST API로만 사용시)

        // h2-console 프레임 옵션 disable (h2-console 접속용)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

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
