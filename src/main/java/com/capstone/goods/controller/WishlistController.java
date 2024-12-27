package com.capstone.goods.controller;

import com.capstone.goods.model.WishlistDto;
import com.capstone.goods.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/wishlists")
@AllArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping()
    public ResponseEntity<WishlistDto> addlike(@RequestHeader("Authorization") String token, @RequestParam Long goodsId) {
        token = token.replace("Bearer ", "");
        WishlistDto wishlistDto = wishlistService.addlike(token, goodsId);
        return new ResponseEntity<>(wishlistDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletelike(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = token.replace("Bearer ", "");
        return wishlistService.deletelike(token, id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        return wishlistService.deleteAlllike(token);
    }
}
