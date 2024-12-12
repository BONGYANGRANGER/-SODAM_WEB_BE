package com.capstone.user.service;

import com.capstone.user.model.User;
import com.capstone.user.model.UserDTO;

public class UserConverter {
    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setBusinessNumber(dto.getBusinessNumber());
        user.setBusinessName(dto.getBusinessName());
        user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        // 비밀번호 제외
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setBusinessNumber(user.getBusinessNumber());
        dto.setBusinessName(user.getBusinessName());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
