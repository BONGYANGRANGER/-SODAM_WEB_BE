package com.capstone.goods.controller;

import com.capstone.goods.model.OrderDto;
import com.capstone.goods.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestParam Long goodsId,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String token) {
        token = extractToken(token);
        return orderService.createOrder(goodsId, quantity, token);
    }

    private String extractToken(String token) {
        return token.replace("Bearer ", "");
    }
}
