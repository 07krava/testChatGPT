package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.BookingService;
import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.ErrorsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/housing/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        try {
            bookingService.createBooking(booking);
            return ResponseEntity.ok("The housing is booked successfully ");
        } catch (RuntimeException e) {
            ErrorsCode errorCode = determineErrorCode(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorCode.getDescription());
        }
    }

    @GetMapping("/allBookingsById/{id}")
    public List<Booking> getBookingsByUserId(@PathVariable Long id) {
        return bookingService.getBookingsByUserId(id);
    }

    private ErrorsCode determineErrorCode(String errorMessage) {
        if (errorMessage.contains(ErrorsCode.INSUFFICIENT_FUNDS.getDescription())) {
            return ErrorsCode.INSUFFICIENT_FUNDS;
        } else if (errorMessage.contains(ErrorsCode.MAXIMUM_GUESTS_EXCEEDED.getDescription())) {
            return ErrorsCode.MAXIMUM_GUESTS_EXCEEDED;
        } else if (errorMessage.contains(ErrorsCode.HOUSING_ALREADY_BOOKED.getDescription())) {
            return ErrorsCode.HOUSING_ALREADY_BOOKED;
        } else {
            // Вернуть код ошибки по умолчанию или обработать другие ошибки, если необходимо
            return ErrorsCode.DEFAULT_ERROR_CODE;
        }
    }
}
