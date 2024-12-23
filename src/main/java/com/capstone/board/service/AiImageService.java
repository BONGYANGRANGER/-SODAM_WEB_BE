package com.capstone.board.service;

import com.capstone.board.db.AiImageRepository;
import com.capstone.board.model.AiImageDto;
import com.capstone.board.model.AiImageEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AiImageService {

    private final AiImageRepository aiImageRepository;

    // 예시 ngrok 주소 (Colab의 AI 서버)
    private static final String AI_SERVER_URL = "https://568a-34-125-213-160.ngrok-free.app/generate_image/";

    public AiImageDto requestAiImage(String prompt) {
        String safePrompt = sanitizePrompt(prompt);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"text\":\"%s\"}", safePrompt.replace("\"", "\\\""));
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                AI_SERVER_URL,
                HttpMethod.POST,
                entity,
                String.class
        );
        String imageUrl = parseImageUrl(response.getBody());

        AiImageEntity e = AiImageEntity.builder()
                .imageType("AI_GENERATED")
                .prompt(safePrompt)
                .imageUrl(imageUrl)
                .build();

        AiImageEntity saved = aiImageRepository.save(e);
        return AiConverter.toAiImageDto(saved);
    }

    public AiImageDto uploadImage(MultipartFile file) {
        checkFileSafety(file);
        String path = saveFile(file);

        AiImageEntity e = AiImageEntity.builder()
                .imageType("USER_UPLOAD")
                .prompt(null)
                .imageUrl(path)
                .build();

        AiImageEntity saved = aiImageRepository.save(e);
        return AiConverter.toAiImageDto(saved);
    }

    private void checkFileSafety(MultipartFile file) {
        if(file.isEmpty()) {
            throw new RuntimeException("파일이 비어있음");
        }
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("이미지 파일만 업로드 가능");
        }
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if(ext == null) {
            throw new RuntimeException("확장자 없음");
        }
        ext = ext.toLowerCase();
        if(!("png".equals(ext) || "jpg".equals(ext) || "jpeg".equals(ext))) {
            throw new RuntimeException("png/jpg/jpeg만 허용");
        }
    }

    private String saveFile(MultipartFile file) {
        try {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String newName = UUID.randomUUID().toString() + "." + ext;
            String baseDir = "uploads"; // 예시: 프로젝트 내부 or 절대 경로
            File dir = new File(baseDir);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            File dest = new File(dir, newName);
            file.transferTo(dest);
            // 실제 운영환경에서는 S3 업로드 후 URL 반환
            return dest.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    private String parseImageUrl(String body) {
        if(body == null) return null;
        int idx = body.indexOf("\"image_url\"");
        if(idx == -1) return null;
        int colon = body.indexOf(":", idx) + 1;
        int quoteStart = body.indexOf("\"", colon) + 1;
        int quoteEnd = body.indexOf("\"", quoteStart);
        return body.substring(quoteStart, quoteEnd);
    }

    private String sanitizePrompt(String prompt) {
        if(prompt == null) return "";
        return prompt.trim();
    }
}
