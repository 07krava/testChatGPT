package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HousingRepository extends JpaRepository<Housing, Long> {
    @Query("SELECT h FROM Housing h JOIN h.location loc WHERE loc.city = :city")
    List<Housing> findByCity(@Param("city") String city);

}
