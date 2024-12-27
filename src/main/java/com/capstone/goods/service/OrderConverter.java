package com.capstone.goods.service;

import com.capstone.goods.model.Order;
import com.capstone.goods.model.OrderDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class OrderConverter {

    public OrderDto entityToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
