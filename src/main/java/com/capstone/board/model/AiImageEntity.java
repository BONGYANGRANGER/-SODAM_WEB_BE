package com.capstone.board.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageType; // "AI_GENERATED" or "USER_UPLOAD"

    @Column(length = 1000)
    private String prompt;

    private String imageUrl;
}
