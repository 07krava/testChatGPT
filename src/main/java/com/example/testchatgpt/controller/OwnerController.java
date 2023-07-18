package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.BookingService;
import com.example.testchatgpt.Service.UserService;
import com.example.testchatgpt.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public OwnerController(UserService userService, BookingService bookingService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/bookings/{ownerId}")
    public List<Booking> getBookingsForOwner(@PathVariable Long ownerId) {
        return bookingService.getBookingsForOwner(ownerId);
    }

    @PutMapping("/approve/{bookingId}")
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
        bookingService.approveBooking(bookingId);
        return ResponseEntity.ok("Booking approved successfully.");
    }

    @PutMapping("/reject/{bookingId}")
    public ResponseEntity<String> rejectBooking(@PathVariable Long bookingId) {
        bookingService.rejectBooking(bookingId);
        return ResponseEntity.ok("Booking rejected successfully.");
    }
}
