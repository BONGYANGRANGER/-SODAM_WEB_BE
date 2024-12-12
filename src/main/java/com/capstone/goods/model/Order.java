package com.capstone.goods.model;

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

    @NotNull
    private Long goodsId;

    @NotBlank
    @Size(min = 2, max = 50)
    private String buyerId;

    @Min(1)
    private int quantity;

    @Min(0)
    private int totalPrice;

    private LocalDateTime orderDate;
}
