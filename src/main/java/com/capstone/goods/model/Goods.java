package com.capstone.goods.model;

import com.capstone.user.model.User;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 30)
    private String category;

    @Min(0)
    private int price;

    @Min(1)
    private int minQuantity;

    @Min(0)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryRegion deliveryRegion = DeliveryRegion.ALL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    public enum DeliveryRegion {
        ALL,    // 전체 지역 배송 가능
        LOCAL   // 특정 지역 배송 가능
    }
}