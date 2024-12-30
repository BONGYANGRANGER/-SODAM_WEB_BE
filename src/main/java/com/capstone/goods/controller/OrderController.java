package com.capstone.goods.controller;

import com.capstone.goods.model.Order;
import com.capstone.goods.model.OrderDto;
import com.capstone.goods.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(
            @RequestParam Long goodsId,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");

        return orderService.createOrder(goodsId, quantity, token);
    }
}