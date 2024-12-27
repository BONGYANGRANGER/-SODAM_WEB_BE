package com.capstone.board.service;

import com.capstone.board.db.DiaryRepository;
import com.capstone.board.model.DiaryDto;
import com.capstone.board.model.DiaryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final AiImageService aiImageService;

    public DiaryDto createDiary(DiaryDto dto, MultipartFile file) {
        String imageUrl = null;
        if("UPLOAD".equalsIgnoreCase(dto.getImageOption())) {
            if(file == null || file.isEmpty()) {
                throw new RuntimeException("파일이 필요합니다.");
            }
            imageUrl = aiImageService.uploadImage(file).getImageUrl();
        } else if("AI".equalsIgnoreCase(dto.getImageOption())) {
            imageUrl = aiImageService.requestAiImage(dto.getContent()).getImageUrl();
        }

        DiaryEntity e = DiaryEntity.builder()
                .date(dto.getDate())
                .content(dto.getContent())
                .imageOption(dto.getImageOption())
                .imageUrl(imageUrl)
                .build();
        DiaryEntity saved = diaryRepository.save(e);
        return AiConverter.toDiaryDto(saved);
    }

    public DiaryDto getDiary(Long id) {
        Optional<DiaryEntity> opt = diaryRepository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("일지를 찾을 수 없습니다. id=" + id);
        }
        return AiConverter.toDiaryDto(opt.get());
    }

    // 필요 시 목록 조회, 수정, 삭제 등 추가
}
