package com.capstone.board.controller;

import com.capstone.board.model.AiImageDto;
import com.capstone.board.service.AiImageService;
import io.swagger.v3.oas.annotations.tags.Tag;  // Swagger 그룹화(태그)용
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "AI Image", description = "AI 이미지를 생성하거나 업로드하는 API")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiImageController {

    private final AiImageService aiImageService;

    @PostMapping("/generate")
    public ResponseEntity<AiImageDto> generateImage(@RequestParam("text") String text) {
        AiImageDto result = aiImageService.requestAiImage(text);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AiImageDto> uploadImage(@RequestPart("file") MultipartFile file) {
        AiImageDto result = aiImageService.uploadImage(file);
        return ResponseEntity.ok(result);
    }
}
