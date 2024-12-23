package com.capstone.board.service;

import com.capstone.board.model.*;

public class AiConverter {

    public static AiImageDto toAiImageDto(AiImageEntity e) {
        if(e == null) return null;
        return AiImageDto.builder()
                .id(e.getId())
                .imageType(e.getImageType())
                .prompt(e.getPrompt())
                .imageUrl(e.getImageUrl())
                .build();
    }

    public static AiImageEntity toAiImageEntity(AiImageDto d) {
        if(d == null) return null;
        return AiImageEntity.builder()
                .id(d.getId())
                .imageType(d.getImageType())
                .prompt(d.getPrompt())
                .imageUrl(d.getImageUrl())
                .build();
    }

    public static DiaryDto toDiaryDto(DiaryEntity e) {
        if(e == null) return null;
        return DiaryDto.builder()
                .id(e.getId())
                .date(e.getDate())
                .content(e.getContent())
                .imageOption(e.getImageOption())
                .imageUrl(e.getImageUrl())
                .build();
    }

    public static DiaryEntity toDiaryEntity(DiaryDto d) {
        if(d == null) return null;
        return DiaryEntity.builder()
                .id(d.getId())
                .date(d.getDate())
                .content(d.getContent())
                .imageOption(d.getImageOption())
                .imageUrl(d.getImageUrl())
                .build();
    }
}
