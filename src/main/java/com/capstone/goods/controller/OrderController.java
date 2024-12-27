package com.capstone.goods.controller;

import com.capstone.goods.model.OrderDto;
import com.capstone.goods.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public String createOrder(
            @RequestParam Long goodsId,
            @RequestParam int quantity,
            Authentication authentication) {
        String buyerId = (authentication != null) ? authentication.getName() : "guest";
        return orderService.createOrder(buyerId, goodsId, quantity);
    }
}
