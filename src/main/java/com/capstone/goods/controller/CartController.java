package com.capstone.goods.controller;

import com.capstone.goods.model.CartDto;
import com.capstone.goods.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> addCart(@RequestHeader("Authorization") String token, @RequestParam Long goodsId) {
        token = extractToken(token);
        CartDto createdCartDto = cartService.createCart(token, goodsId);
        return new ResponseEntity<>(createdCartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = extractToken(token);
        return cartService.deleteCart(token, id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(@RequestHeader("Authorization") String token) {
        token = extractToken(token);
        return cartService.deleteAllCart(token);
    }

    private String extractToken(String token) {
        return token.replace("Bearer ", "");
    }
}
