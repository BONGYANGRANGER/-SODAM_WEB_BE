package com.capstone.goods.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @NotNull
    private int quantity;

    @NotNull
    private int totalPrice;

    private Long goodsId;

    private String message;

    @JsonSetter(nulls = Nulls.SKIP)
    private Order.Role role = Order.Role.SHIPPING;
}