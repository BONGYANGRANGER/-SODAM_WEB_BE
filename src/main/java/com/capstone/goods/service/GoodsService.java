package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.GoodsDto;
import com.capstone.jwt.TokenProvider;
import com.capstone.user.db.UserRepository;
import com.capstone.user.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsConverter goodsConverter;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public List<GoodsDto> getAllGoods() {
        return goodsRepository.findAll().stream()
                .map(goodsConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public GoodsDto getGoodsById(Long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return goodsConverter.entityToDto(goods);
    }

    @Transactional
    public void addGoods(GoodsDto goodsDto, String token) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (goodsRepository.existsByNameAndCategory(goodsDto.getName(), goodsDto.getCategory())) {
            throw new IllegalArgumentException("이미 동일한 이름과 카테고리를 가진 상품이 존재합니다.");
        }

        Goods goods = goodsConverter.dtoToEntity(goodsDto, user);
        goodsRepository.save(goods);
    }

    public GoodsDto modifyGoods(Long id, GoodsDto goodsDto) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        goods.setName(goodsDto.getName());
        goods.setCategory(goodsDto.getCategory());
        goods.setPrice(goodsDto.getPrice());
        goods.setStock(goodsDto.getStock());
        goodsRepository.save(goods);

        return goodsConverter.entityToDto(goods);
    }

    // 상품 삭제
    public void deleteGoods(Long id) {
        goodsRepository.deleteById(id);
    }
}
