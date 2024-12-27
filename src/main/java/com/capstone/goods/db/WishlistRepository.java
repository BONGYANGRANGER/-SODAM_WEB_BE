package com.capstone.goods.db;

import com.capstone.goods.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}

