package com.capstone.board.service;

import com.capstone.board.db.DiaryRepository;
import com.capstone.board.model.DiaryDto;
import com.capstone.board.model.DiaryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryDto createDiary(DiaryDto dto, MultipartFile file) {
        String imageUrl = null;

        if ("UPLOAD".equalsIgnoreCase(dto.getImageOption())) {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("파일이 필요합니다.");
            }
            imageUrl = uploadImage(file);
        }

        DiaryEntity entity = DiaryEntity.builder()
                .date(dto.getDate())
                .content(dto.getContent())
                .imageOption(dto.getImageOption())
                .imageUrl(imageUrl)
                .build();

        DiaryEntity saved = diaryRepository.save(entity);
        return toDiaryDto(saved);
    }

    public DiaryDto getDiary(Long id) {
        Optional<DiaryEntity> optional = diaryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("일지를 찾을 수 없습니다. id=" + id);
        }
        return toDiaryDto(optional.get());
    }

    private String uploadImage(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/images/diary", fileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
        return "/uploads/images/diary/" + fileName;
    }

    private DiaryDto toDiaryDto(DiaryEntity entity) {
        return DiaryDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .content(entity.getContent())
                .imageOption(entity.getImageOption())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
