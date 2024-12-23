package com.capstone.board.controller;

import com.capstone.board.model.DiaryDto;
import com.capstone.board.service.DiaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "Diary", description = "농가 일지 작성 및 조회 API")
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * (1) JSON 전용 (AI 시나리오 등)
     * 파일 없이 보내는 경우
     * imageOption="AI" 가 주로 사용
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiaryDto> createDiaryJson(@RequestBody DiaryDto data) {
        if ("UPLOAD".equalsIgnoreCase(data.getImageOption())) {
            throw new RuntimeException("파일 업로드는 multipart/form-data로 전송해주세요.");
        }
        DiaryDto saved = diaryService.createDiary(data, null);
        return ResponseEntity.ok(saved);
    }

    /**
     * (2) 멀티파트 전용 (파일 업로드 시나리오)
     * imageOption="UPLOAD" 권장, 하지만 파일이 없어도 처리 가능하도록 수정
     */
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryDto> createDiaryMultipart(
            @RequestPart("data") DiaryDto data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // 만약 data.getImageOption()이 "UPLOAD"가 아니더라도, 파일 업로드용 API이므로 일괄 처리
        if (!"UPLOAD".equalsIgnoreCase(data.getImageOption())) {
            data.setImageOption("UPLOAD");
        }
        DiaryDto saved = diaryService.createDiary(data, file);
        return ResponseEntity.ok(saved);
    }

    /**
     * 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiaryDto> getDiary(@PathVariable Long id) {
        DiaryDto dto = diaryService.getDiary(id);
        return ResponseEntity.ok(dto);
    }
}
