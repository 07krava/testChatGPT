package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HousingRepository extends JpaRepository<Housing, Long> {

    List<Housing> findByIsActiveTrue();
}
