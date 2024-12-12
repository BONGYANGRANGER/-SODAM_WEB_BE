package com.capstone.goods.service;

import com.capstone.goods.model.Goods;
import com.capstone.goods.model.GoodsDto;
import org.springframework.stereotype.Component;

@Component
public class GoodsConverter {

    public GoodsDto entityToDto(Goods goods) {
        return GoodsDto.builder()
                .id(goods.getId())
                .name(goods.getName())
                .category(goods.getCategory())
                .price(goods.getPrice())
                .stock(goods.getStock())
                .description(goods.getDescription())
                .build();
    }

    public Goods dtoToEntity(GoodsDto goodsDto) {
        return Goods.builder()
                .id(goodsDto.getId())
                .name(goodsDto.getName())
                .category(goodsDto.getCategory())
                .price(goodsDto.getPrice())
                .stock(goodsDto.getStock())
                .description(goodsDto.getDescription())
                .build();
    }
}
