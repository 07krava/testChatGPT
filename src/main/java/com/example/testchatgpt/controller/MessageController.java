package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.MessageService;
import com.example.testchatgpt.dto.MessageDTO;
import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

    private final MessageService messageService;
    private final BookingRepository bookingRepository;

    @Autowired
    public MessageController(MessageService messageService, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam Long senderId, @RequestParam Long recipientId,
                                              @RequestParam String content) {
        User sender = new User(senderId);
        User recipient = new User(recipientId);
        if (content.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message cannot be empty! ");
        } else {
            messageService.sendMessage(sender, recipient, content);
            return ResponseEntity.ok("Message sent successfully");
        }
    }

    @GetMapping("/{user1Id}/{user2Id}")
    public List<MessageDTO> getMessagesByUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        User sender = new User();
        sender.setId(user1Id);

        User recipient = new User();
        recipient.setId(user2Id);

        return messageService.getMessagesByUsers(sender, recipient);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getMessagesByUsers(@PathVariable Long bookingId) {

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            List<MessageDTO> messages = messageService.getMessagesByBookingId(bookingId);
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking cannot be empty!");
        }
    }
}
