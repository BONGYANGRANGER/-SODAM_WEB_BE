package com.capstone.goods.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

    private Long id;
    private Long goodsId;
}
