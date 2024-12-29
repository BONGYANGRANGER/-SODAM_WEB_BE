package com.capstone.goods.controller;

import com.capstone.goods.model.GoodsDto;
import com.capstone.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<GoodsDto> addGoods(
            @RequestPart("data") GoodsDto data,
            @RequestPart("file") MultipartFile file) {
        GoodsDto saved = goodsService.addGoods(data, file);
        return ResponseEntity.ok(saved);
    }
}
