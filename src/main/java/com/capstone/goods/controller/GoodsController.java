package com.capstone.goods.controller;

import com.capstone.goods.model.GoodsDto;
import com.capstone.goods.service.GoodsService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping
    public List<GoodsDto> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @GetMapping("/{id}")
    public GoodsDto getGoodsById(@PathVariable Long id) {
        return goodsService.getGoodsById(id);
    }

    @PatchMapping("/modify/{id}")
    public void modifyGoodsById(
            @PathVariable Long id,
            @RequestBody GoodsDto goodsDto) {

        goodsService.modifyGoods(id, goodsDto);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addGoods(@RequestBody @Valid GoodsDto goodsDto, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        goodsService.addGoods(goodsDto, token);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public void deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
    }
}
