package com.capstone.goods.model;

import com.capstone.user.model.User;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private int quantity;

    @Min(0)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.SHIPPING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public enum Role {
        SHIPPING,
        DELIVERED
    }
}