package com.capstone.goods.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private Long goodsId;
    private String buyerId;
    private int quantity;
    private int totalPrice;
    private String orderDate;
}
