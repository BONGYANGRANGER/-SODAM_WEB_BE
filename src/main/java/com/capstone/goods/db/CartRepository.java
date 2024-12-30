package com.capstone.goods.db;

import com.capstone.goods.model.Cart;
import com.capstone.goods.model.Goods;
import com.capstone.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndGoods(User user, Goods goods);

    List<Cart> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}