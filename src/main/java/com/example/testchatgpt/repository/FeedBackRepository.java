package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Feedback;
import com.example.testchatgpt.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByHousingId(Housing housing);
}
