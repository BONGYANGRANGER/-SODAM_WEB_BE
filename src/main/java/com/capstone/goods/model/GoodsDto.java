package com.capstone.goods.model;

import com.capstone.user.model.User;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    private int price;

    @NotNull
    private int minQuantity;

    @NotNull
    private int stock;

    @JsonSetter(nulls = Nulls.SKIP)
    private Goods.DeliveryRegion deliveryRegion = Goods.DeliveryRegion.ALL;
}
