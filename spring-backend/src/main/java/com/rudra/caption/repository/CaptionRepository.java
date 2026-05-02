package com.rudra.caption.repository;

import com.rudra.caption.model.Caption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptionRepository extends JpaRepository<Caption, Integer> {
}