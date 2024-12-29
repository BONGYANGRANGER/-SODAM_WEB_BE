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

    private String date;

    @Column(length = 2000)
    private String content;

    private String imageOption;

    private String imageUrl;
}
