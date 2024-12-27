package com.capstone.config;

import com.capstone.jwt.filter.TokenAuthenticationFilter;
import com.capstone.jwt.filter.TokenExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.core.annotation.Order;
=======
import org.springframework.security.config.Customizer;
>>>>>>> 761439f21bb69bd3039ca3b02184dc51a9d6fdd2
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security 설정 클래스
 * - Spring Boot 3.x / Spring Security 6.x 기준
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

<<<<<<< HEAD
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenExceptionFilter tokenExceptionFilter;

    @Bean
    @Order(1)
    SecurityFilterChain apiFilter(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 경로 허용
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/users/login/**"),
                                new AntPathRequestMatcher("/api/users/signup")
                        )
                        .permitAll() // 특정 경로 허용
                        .anyRequest()
                        .authenticated() // 나머지 요청은 인증 필요
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenExceptionFilter, TokenAuthenticationFilter.class);
=======
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
>>>>>>> 761439f21bb69bd3039ca3b02184dc51a9d6fdd2

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