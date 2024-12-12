package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.GoodsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsConverter goodsConverter;

    public GoodsService(GoodsRepository goodsRepository, GoodsConverter goodsConverter) {
        this.goodsRepository = goodsRepository;
        this.goodsConverter = goodsConverter;
    }

    public List<GoodsDto> getAllGoods() {
        return goodsRepository.findAll().stream()
                .map(goodsConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public GoodsDto getGoodsById(Long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return goodsConverter.entityToDto(goods);
    }

    public void addGoods(GoodsDto goodsDto) {
        Goods goods = goodsConverter.dtoToEntity(goodsDto);
        goodsRepository.save(goods);
    }

    public void deleteGoods(Long id) {
        goodsRepository.deleteById(id);
    }
}
