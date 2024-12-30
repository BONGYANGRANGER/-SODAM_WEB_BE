package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.db.OrderRepository;
import com.capstone.goods.model.*;
import com.capstone.jwt.TokenProvider;
import com.capstone.user.db.UserRepository;
import com.capstone.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;


    public ResponseEntity<OrderDto> createOrder(Long goodsId, int quantity, String token) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        if (goods.getStock() < quantity) {
            // 재고 부족 시 오류 메시지만 반환
            OrderDto orderDto = OrderDto.builder()
                    .quantity(0)
                    .totalPrice(0)
                    .goodsId(goodsId)
                    .message("재고가 부족합니다.")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDto);
        }

        // 정상적으로 주문 생성
        Order order = Order.builder()
                .quantity(quantity)
                .totalPrice(goods.getPrice() * quantity)
                .user(user)
                .goods(goods)
                .role(Order.Role.SHIPPING)
                .build();

        goods.setStock(goods.getStock() - quantity);
        goodsRepository.save(goods);
        orderRepository.save(order);

        // 정상적인 주문 생성 후 200 OK 상태 코드 반환
        OrderDto orderDto = OrderDto.builder()
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .goodsId(order.getGoods().getId())
                .role(order.getRole())
                .build();
        return ResponseEntity.ok(orderDto);
    }

}