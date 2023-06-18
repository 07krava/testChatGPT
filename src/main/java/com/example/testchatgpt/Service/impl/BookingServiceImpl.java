package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.BookingService;
import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.User;
import com.example.testchatgpt.repository.BookingRepository;
import com.example.testchatgpt.repository.HousingRepository;
import com.example.testchatgpt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final HousingRepository housingRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(HousingRepository housingRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.housingRepository = housingRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public boolean isHousingAvailableByDates(Housing housing, Date startDate, Date endDate) {
        List<Booking> bookings = bookingRepository.findByHousingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(housing, startDate, endDate);
        return bookings.isEmpty();
    }

    public Booking createBooking(Booking booking) {

        User user = userRepository.findById(booking.getRenter().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + booking.getRenter().getId()));
        Housing housing = housingRepository.findById(booking.getHousing().getId()).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + booking.getHousing().getId()));
        boolean isHousingAvailable = isHousingAvailableByDates(booking.getHousing(), booking.getStartDate(), booking.getEndDate());
        if (!isHousingAvailable) {
            throw new RuntimeException("The housing is not available for the selected dates");
        }
            if(housing.getMaxAmountPeople() == booking.getGuests()) {
                booking.setOwner(user);
                booking.setHousing(housing);
                booking.setOwner(housing.getOwner());
                bookingRepository.save(booking);
            }
            else{
                throw new RuntimeException("Can't fit that many people");
            }
            return booking;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<Booking> listBooking = user.getBookings();
        return listBooking;
    }
}
