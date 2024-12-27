package com.capstone.goods.db;

import com.capstone.goods.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    boolean existsByNameAndCategory(String name, String category);
}
