package com.capstone.goods.controller;

import com.capstone.goods.model.CartDto;
import com.capstone.goods.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<CartDto> addcart(@RequestHeader("Authorization") String token, @RequestParam Long goodsId) {
        token = token.replace("Bearer ", "");
        CartDto createdCartDto = cartService.createCart(token, goodsId);
        return new ResponseEntity<>(createdCartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletecart(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = token.replace("Bearer ", "");
        return cartService.deleteCart(token, id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return cartService.deleteAllCart(token);
    }
}