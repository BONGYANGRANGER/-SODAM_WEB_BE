package com.capstone.board.db;

import com.capstone.board.model.AiImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiImageRepository extends JpaRepository<AiImageEntity, Long> {
}
