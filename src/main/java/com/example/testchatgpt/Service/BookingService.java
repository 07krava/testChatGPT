package com.example.testchatgpt.Service;

import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.Housing;

import java.util.Date;
import java.util.List;

public interface BookingService {

    List<Housing> findAvailableHousingByDates(Date startDate, Date endDate);

    boolean isHousingAvailableByDates(Housing housing, Date startDate, Date endDate);

    Booking createBooking(Booking booking);

    List<Booking> getBookingsByUserId(Long userId);

//    List<Housing> getBookedHousing(Date startDate, Date endDate);
//
//    List<Housing> getAvailableHousings(Date startDate, Date endDate);
}
