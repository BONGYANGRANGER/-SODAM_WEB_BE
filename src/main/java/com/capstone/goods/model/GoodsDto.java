package com.capstone.goods.model;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {

    private Long id;
    private String name;
    private String category;
    private int price;
    private int stock;
    private String description;
}
