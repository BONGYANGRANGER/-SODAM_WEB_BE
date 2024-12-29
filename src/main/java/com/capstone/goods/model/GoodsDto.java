package com.capstone.goods.model;


import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    private Long id;
    private String name;
    private String category;
    private int price;
    private int minQuantity;
    private int stock;
    @JsonSetter(nulls = Nulls.SKIP)
    private Goods.DeliveryRegion deliveryRegion = Goods.DeliveryRegion.ALL;
    private String imageUrl;
}

