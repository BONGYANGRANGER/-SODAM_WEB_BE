package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.GoodsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsDto addGoods(GoodsDto dto, MultipartFile file) {
        String imageUrl = uploadImage(file);

        Goods goods = Goods.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .minQuantity(dto.getMinQuantity())
                .stock(dto.getStock())
                .imageUrl(imageUrl)
                .build();

        Goods saved = goodsRepository.save(goods);
        return toGoodsDto(saved);
    }

    private String uploadImage(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/images/goods", fileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
        return "/uploads/images/goods/" + fileName;
    }

    private GoodsDto toGoodsDto(Goods goods) {
        return GoodsDto.builder()
                .id(goods.getId())
                .name(goods.getName())
                .category(goods.getCategory())
                .price(goods.getPrice())
                .minQuantity(goods.getMinQuantity())
                .stock(goods.getStock())
                .imageUrl(goods.getImageUrl())
                .build();
    }
}
