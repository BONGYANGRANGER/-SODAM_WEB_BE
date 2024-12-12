package com.capstone.goods.controller;

import com.capstone.goods.model.OrderDto;
import com.capstone.goods.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> getOrders(Authentication authentication) {
        // 인증없이 접근 허용 상태이므로 authentication이 null일 수 있음
        String buyerId = (authentication != null) ? authentication.getName() : "guest";
        return orderService.getOrdersByBuyer(buyerId);
    }

    @PostMapping
    public String createOrder(
            @RequestParam Long goodsId,
            @RequestParam int quantity,
            Authentication authentication) {
        String buyerId = (authentication != null) ? authentication.getName() : "guest";
        return orderService.createOrder(buyerId, goodsId, quantity);
    }
}
