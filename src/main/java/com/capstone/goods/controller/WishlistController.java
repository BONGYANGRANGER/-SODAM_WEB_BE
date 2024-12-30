package com.capstone.goods.controller;

import com.capstone.goods.model.WishlistDto;
import com.capstone.goods.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
@AllArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistDto> addLike(@RequestHeader("Authorization") String token, @RequestParam Long goodsId) {
        token = extractToken(token);
        WishlistDto wishlistDto = wishlistService.addlike(token, goodsId);
        return new ResponseEntity<>(wishlistDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLike(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = extractToken(token);
        return wishlistService.deletelike(token, id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(@RequestHeader("Authorization") String token) {
        token = extractToken(token);
        return wishlistService.deleteAlllike(token);
    }

    private String extractToken(String token) {
        return token.replace("Bearer ", "");
    }
}
