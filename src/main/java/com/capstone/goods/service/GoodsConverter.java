package com.capstone.goods.service;

import com.capstone.goods.model.Goods;
import com.capstone.goods.model.GoodsDto;
import com.capstone.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class GoodsConverter {

    // Goods -> GoodsDto 변환
    public GoodsDto entityToDto(Goods goods) {
        return GoodsDto.builder()
                .name(goods.getName())
                .category(goods.getCategory())
                .price(goods.getPrice())
                .stock(goods.getStock())
                .minQuantity(goods.getMinQuantity())
                .deliveryRegion(goods.getDeliveryRegion())
                .imageUrl(goods.getImageUrl()) // 이미지 URL 추가
                .build();
    }

    // GoodsDto -> Goods 변환 (User 매핑 포함)
    public Goods dtoToEntity(GoodsDto goodsDto, User user) {
        return Goods.builder()
                .name(goodsDto.getName())
                .category(goodsDto.getCategory())
                .price(goodsDto.getPrice())
                .stock(goodsDto.getStock())
                .minQuantity(goodsDto.getMinQuantity())
                .deliveryRegion(goodsDto.getDeliveryRegion())
                .imageUrl(goodsDto.getImageUrl()) // 이미지 URL 추가
                .user(user)
                .build();
    }
}
