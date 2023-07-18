package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.MessageService;
import com.example.testchatgpt.dto.MessageDTO;
import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.Message;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.repository.BookingRepository;
import com.example.testchatgpt.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(User sender, User recipient, String content) {

        if (content.equals("")){
            throw new RuntimeException("Message cannot be empty!");
        }else {

            Message message = new Message();
            message.setSender(sender);
            message.setRecipient(recipient);
            message.setText(content);
            message.setTimestamp(LocalDateTime.now());
            messageRepository.save(message);
        }
    }

    public List<MessageDTO> getMessagesByUsers(User sender, User recipient) {
        List<Message> messages1 = messageRepository.findBySenderAndRecipient(sender, recipient);
        List<Message> messages2 = messageRepository.findBySenderAndRecipient(recipient, sender);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messages1);
        allMessages.addAll(messages2);

        Collections.sort(allMessages, Comparator.comparing(Message::getTimestamp));

        List<MessageDTO> allMessageDTOs = new ArrayList<>();

        for (Message message : allMessages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setSenderId(message.getSender().getId());
            messageDTO.setRecipientId(message.getRecipient().getId());
            messageDTO.setSenderUsername(message.getSender().getUsername());
            messageDTO.setRecipientUsername(message.getRecipient().getUsername());
            messageDTO.setText(message.getText());
            messageDTO.setTimestamp(message.getTimestamp().withNano(0));
            allMessageDTOs.add(messageDTO);
        }

        return allMessageDTOs;
    }

    @Override
    public List<MessageDTO> getMessagesByBookingId(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        User sender = booking.get().getRenter();
        User recipient = booking.get().getOwner();

        List<Message> messages1 = messageRepository.findBySenderAndRecipient(sender, recipient);
        List<Message> messages2 = messageRepository.findBySenderAndRecipient(recipient, sender);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messages1);
        allMessages.addAll(messages2);

        Collections.sort(allMessages, Comparator.comparing(Message::getTimestamp));

        return  allMessages.stream().map(this::convertToMessageDTO).collect(Collectors.toList());
    }

    private MessageDTO convertToMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setRecipientId(message.getRecipient().getId());
        messageDTO.setSenderUsername(message.getSender().getUsername());
        messageDTO.setRecipientUsername(message.getRecipient().getUsername());
        messageDTO.setText(message.getText());
        messageDTO.setTimestamp(message.getTimestamp().withNano(0));
        return messageDTO;
    }
}
