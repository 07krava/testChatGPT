package com.example.testchatgpt.repository;

import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHousingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Housing housing, Date startDate, Date endDate);
}
