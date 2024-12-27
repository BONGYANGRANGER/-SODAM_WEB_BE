package com.capstone.board.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDto {
    private Long id;
    private String date;
    private String content;
    private String imageOption; // "AI" or "UPLOAD"
    private String imageUrl;
}
