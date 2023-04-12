package com.example.testchatgpt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Location")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country")
    private String country;
    @Column(name = "region")
    private String region;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private int houseNumber;
    @Column(name = "apartment_number")
    private int apartmentNumber;
    @Column(name = "zip_code")
    private String zipCode;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "housing_id", referencedColumnName = "id")
    private Housing housing;

}
