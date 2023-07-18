package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Message;
import com.example.testchatgpt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findBySenderAndRecipient(User sender, User recipient);

}
