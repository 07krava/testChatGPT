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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<Housing> findAvailableHousingByDates(Date startDate, Date endDate) {
        List<Housing> availableHousing = new ArrayList<>();
        List<Housing> allHousing = housingRepository.findAll();
        for (Housing housing : allHousing) {
            boolean isHousingAvailable = isHousingAvailableByDates(housing, startDate, endDate);
            if (isHousingAvailable) {
                availableHousing.add(housing);
            }
        }
        return availableHousing;
    }

    public boolean isHousingAvailableByDates(Housing housing, Date startDate, Date endDate) {
        List<Booking> bookings = bookingRepository.findByHousingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(housing, startDate, endDate);
        return bookings.isEmpty();
    }

    public Booking createBooking(Booking booking) {
        User user = userRepository.findById(booking.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + booking.getUser().getId()));
        Housing housing = housingRepository.findById(booking.getHousing().getId()).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + booking.getHousing().getId()));
        boolean isHousingAvailable = isHousingAvailableByDates(booking.getHousing(), booking.getStartDate(), booking.getEndDate());
        if (!isHousingAvailable) {
            throw new RuntimeException("The housing is not available for the selected dates");
        }
        booking.setUser(user);
        booking.setHousing(housing);
        bookingRepository.save(booking);
        return booking;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<Booking> listBooking = user.getBookings();
        return listBooking;
    }

//    @Override
//    public List<Housing> getBookedHousing(Date startDate, Date endDate) {
//        List<Housing> bookedHousings = new ArrayList<>();
//        List<Booking> bookings = bookingRepository.findAll();
//        for (Housing housing : housingRepository.findAll()) {
//            boolean isBooked = false;
//            for (Booking booking : bookings) {
//                if (booking.getHousing().equals(housing)) {
//                    if (startDate.before(booking.getEndDate()) && endDate.after(booking.getStartDate())) {
//                        isBooked = true;
//                        break;
//                    }
//                }
//            }
//            if (isBooked) {
//                bookedHousings.add(housing);
//            }
//        }
//        return bookedHousings;
//    }
//
//    @Override
//    public List<Housing> getAvailableHousings(Date startDate, Date endDate) {
//        List<Housing> availableHousings = new ArrayList<>();
//        List<Booking> bookings = bookingRepository.findAll();
//        for (Housing housing : housingRepository.findAll()) {
//            boolean isAvailable = true;
//            for (Booking booking : bookings) {
//                if (booking.getHousing().equals(housing)) {
//                    if (startDate.before(booking.getEndDate()) && endDate.after(booking.getStartDate())) {
//                        isAvailable = false;
//                        break;
//                    }
//                }
//            }
//            if (isAvailable) {
//                availableHousings.add(housing);
//            }
//        }
//        return availableHousings;
//    }
}
