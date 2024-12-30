package com.capstone.goods.service;

import com.capstone.goods.db.CartRepository;
import com.capstone.goods.db.GoodsRepository;
import com.capstone.goods.model.Cart;
import com.capstone.goods.model.CartDto;
import com.capstone.goods.model.Goods;
import com.capstone.goods.model.Wishlist;
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
public class CartService {
    private final CartRepository cartRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;

    public CartDto createCart(String token, Long goodsId) {
        // 토큰에서 사용자 ID 추출
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 상품 정보 조회
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // Cart 객체 생성
        Cart cart = Cart.builder()
                .user(user)
                .goods(goods)
                .name(goods.getName())  // name 필드 설정
                .price(goods.getPrice())
                .build();

        // 장바구니에 저장
        cartRepository.save(cart);

        // CartDto 반환 (필요한 필드만 포함)
        return CartDto.builder()
                .goodsId(goods.getId())
                .name(goods.getName())  // name을 goods.getName()으로 반환
                .price(goods.getPrice())
                .build();
    }

    public ResponseEntity<String> deleteCart(String token, Long id) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

        Cart cart = cartRepository.findByUserAndGoods(user, goods)
                .orElseThrow(() -> new RuntimeException("장바구니에 해당 상품이 없습니다"));

        cartRepository.delete(cart);

        return ResponseEntity.status(HttpStatus.OK).body("장바구니가 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<String> deleteAllCart(String token) {
        Long userId = tokenProvider.getClaims(token).get("id", Long.class);

        List<Cart> cart = cartRepository.findAllByUserId(userId);

        if (cart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("장바구니 목록이 비어 있습니다.");
        }


        cartRepository.deleteAllByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body("모든 장바구니 항목이 삭제되었습니다.");
    }
}