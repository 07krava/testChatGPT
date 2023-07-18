package com.example.testchatgpt.errors;

public class HousingNotFoundException extends RuntimeException {
    public HousingNotFoundException(String housingId) {
        super("Housing with ID " + housingId + " not found");
    }
}