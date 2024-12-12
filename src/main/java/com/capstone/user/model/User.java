package com.capstone.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // 테이블명 변경
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    // 비밀번호 길이 제약은 DTO에서 처리하므로 여기서도 유지
    @NotBlank
    @Size(min = 7, max = 60)
    private String password;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{10,11}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank
    @Size(max = 50)
    private String address;

    @Size(max = 50)
    private String businessNumber;

    @Size(max = 50)
    private String businessName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    public enum Role {
        USER, SELLER
    }
}
