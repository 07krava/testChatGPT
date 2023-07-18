package com.example.testchatgpt.errors;

public class EmptyFeedbacksException extends RuntimeException {
    public EmptyFeedbacksException(String s) {
        super("Review from housingId is empty");
    }
}