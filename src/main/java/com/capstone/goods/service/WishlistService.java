package com.capstone.goods.service;

import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.db.WishlistRepository;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.Wishlist;
import com.capstone.goods.model.WishlistDto;
import com.capstone.jwt.TokenProvider;
import com.capstone.user.db.UserRepository;
import com.capstone.user.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class WishlistService {
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final WishlistRepository wishlistRepository;

    public WishlistDto addlike(String token, Long goodsId) {
        // 토큰에서 userId 추출
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        // 상품 정보 가져오기
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // 사용자 정보 가져오기 (토큰에서 추출한 userId 사용)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // Wishlist 객체 생성
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .goods(goods)
                .build();

        wishlistRepository.save(wishlist);

        return WishlistDto.builder()
                .id(wishlist.getId())
                .goodsId(goods.getId())
                .build();
    }

    public ResponseEntity<String> deletelike(String token, Long id) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찜한 상품을 찾을 수 없습니다."));

        if (!wishlist.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제할 권한이 없습니다.");
        }

        wishlistRepository.delete(wishlist);

        return ResponseEntity.status(HttpStatus.OK).body("찜 상품이 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<String> deleteAlllike(String token) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        List<Wishlist> wishlist = wishlistRepository.findAllByUserId(userId);

        if (wishlist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 목록이 비어 있습니다.");
        }

        wishlist.forEach(item -> {
            if (!item.getUser().getId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제할 권한이 없습니다.");
            }
        });
        wishlistRepository.deleteAllByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body("모든 찜 항목이 삭제되었습니다.");
        }
    }
