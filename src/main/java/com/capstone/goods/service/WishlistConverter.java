    package com.capstone.goods.service;

    import com.capstone.goods.model.*;
    import com.capstone.user.model.User;
    import org.springframework.stereotype.Component;

    @Component
    public class WishlistConverter {

        public WishlistDto entityToDto(Wishlist wishlist) {
            return WishlistDto.builder()
                    .id(wishlist.getId())  // Wishlist ID
                    .goodsId(wishlist.getGoods().getId())
                    .build();
        }
    }
