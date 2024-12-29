package com.capstone.board.controller;

import com.capstone.board.model.DiaryDto;
import com.capstone.board.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(path = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<DiaryDto> createDiaryMultipart(
            @RequestPart("data") DiaryDto data,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        DiaryDto saved = diaryService.createDiary(data, file);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryDto> getDiary(@PathVariable Long id) {
        DiaryDto dto = diaryService.getDiary(id);
        return ResponseEntity.ok(dto);
    }
}
