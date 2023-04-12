package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    void deleteByHousingId(Long housingId);
}
