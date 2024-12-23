package com.capstone.board.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiImageDto {
    private Long id;
    private String imageType;
    private String prompt;
    private String imageUrl;
}
