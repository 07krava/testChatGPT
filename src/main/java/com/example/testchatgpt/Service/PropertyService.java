package com.example.testchatgpt.Service;

import com.example.testchatgpt.model.Property;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PropertyService {

    List<Property> getFilteredProperties(BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         String housingType,
                                         Integer bedCount,
                                         Integer bathCount,
                                         Integer maxAmountPeople,
                                         Date startDate,
                                         Date endDate,
                                         Integer beds,
                                         String city);
}
