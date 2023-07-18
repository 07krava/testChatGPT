package com.example.testchatgpt.Service;

import com.example.testchatgpt.dto.MessageDTO;
import com.example.testchatgpt.model.User;

import java.util.List;

public interface MessageService {

    void sendMessage(User sender, User recipient, String content);

    List<MessageDTO> getMessagesByUsers(User sender, User recipient);

    List<MessageDTO> getMessagesByBookingId(Long bookingId);

}
