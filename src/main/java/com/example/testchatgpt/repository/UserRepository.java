package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
