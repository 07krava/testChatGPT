package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Housing, Long>, JpaSpecificationExecutor<Property> {
}
