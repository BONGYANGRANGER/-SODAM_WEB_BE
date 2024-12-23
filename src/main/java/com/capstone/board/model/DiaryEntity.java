package com.capstone.board.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diaries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;  // 예: "2024-12-23"

    @Column(length = 2000)
    private String content;

    private String imageOption; // "AI" or "UPLOAD"

    private String imageUrl;    // 최종 이미지 URL (직접 업로드 or AI 생성)
}
