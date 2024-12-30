package com.capstone.goods.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDto{

    @NotNull
    private String name;

    @NotNull
    private int price;

    private Long goodsId;
}