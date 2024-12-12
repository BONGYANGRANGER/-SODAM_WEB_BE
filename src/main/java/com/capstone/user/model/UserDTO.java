package com.capstone.user.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    @Size(max = 20)
    private String username;

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

    @NotBlank
    private String role; // "USER" 또는 "SELLER"
}
