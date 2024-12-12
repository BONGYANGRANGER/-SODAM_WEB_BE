package com.capstone.goods.controller;

import com.capstone.goods.model.GoodsDto;
import com.capstone.goods.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping
    public List<GoodsDto> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @GetMapping("/{id}")
    public GoodsDto getGoodsById(@PathVariable Long id) {
        return goodsService.getGoodsById(id);
    }

    @PostMapping
    public void addGoods(@RequestBody GoodsDto goodsDto) {
        goodsService.addGoods(goodsDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
    }
}
