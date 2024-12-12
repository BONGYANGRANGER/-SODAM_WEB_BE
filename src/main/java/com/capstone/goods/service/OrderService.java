package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.db.OrderRepository;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.Order;
import com.capstone.goods.model.OrderDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final OrderConverter orderConverter;

    public OrderService(OrderRepository orderRepository, GoodsRepository goodsRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.orderConverter = orderConverter;
    }

    public List<OrderDto> getOrdersByBuyer(String buyerId) {
        return orderRepository.findAllByBuyerId(buyerId).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public String createOrder(String buyerId, Long goodsId, int quantity) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        if (goods.getStock() < quantity) {
            return "재고가 부족합니다.";
        }

        Order order = Order.builder()
                .buyerId(buyerId)
                .goodsId(goodsId)
                .quantity(quantity)
                .totalPrice(goods.getPrice() * quantity)
                .orderDate(LocalDateTime.now())
                .build();

        goods.setStock(goods.getStock() - quantity);
        goodsRepository.save(goods);
        orderRepository.save(order);

        return "주문이 완료되었습니다.";
    }
}
