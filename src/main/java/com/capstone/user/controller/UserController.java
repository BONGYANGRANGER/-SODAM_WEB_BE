package com.capstone.user.controller;

import com.capstone.jwt.model.AccessTokenRequestDto;
import com.capstone.jwt.model.AccessTokenResponseDto;
import com.capstone.jwt.model.CreateAccessTokenByRefreshTokenDto;
import com.capstone.jwt.service.TokenService;
import com.capstone.user.model.UserDTO;
import com.capstone.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> login(
            @RequestBody AccessTokenRequestDto request
    ) {
        AccessTokenResponseDto token = tokenService.getAccessToken(request);
        if(token != null){
            return ResponseEntity.ok().body(token);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login/token")
    public ResponseEntity<AccessTokenResponseDto> tokenLogin(
            @RequestBody CreateAccessTokenByRefreshTokenDto request
    ) {
        AccessTokenResponseDto responseDto = tokenService.refreshAccessToken(request);
        if(responseDto != null)
            return ResponseEntity.ok().body(responseDto);
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserDtoById(id);
        return ResponseEntity.ok(userDTO);
        }
    }



