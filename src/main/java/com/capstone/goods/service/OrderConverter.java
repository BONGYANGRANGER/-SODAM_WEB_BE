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
                .goodsId(order.getGoodsId())
                .buyerId(order.getBuyerId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
