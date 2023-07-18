package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.FeedbackService;
import com.example.testchatgpt.Service.HousingService;
import com.example.testchatgpt.Service.UserService;
import com.example.testchatgpt.dto.FeedbackDTO;
import com.example.testchatgpt.dto.HousingDTO;
import com.example.testchatgpt.errors.EmptyFeedbacksException;
import com.example.testchatgpt.errors.HousingNotFoundException;
import com.example.testchatgpt.model.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final HousingService housingService;

    public FeedbackController(FeedbackService feedbackService, UserService userService, HousingService housingService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.housingService = housingService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createFeedback(@RequestBody Feedback feedback) {
        if (!feedback.getComment().equals("")) {
            feedbackService.createFeedback(feedback);
            return ResponseEntity.ok("Feedback add successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Feedback cannot be empty!");
        }
    }

    @GetMapping("/getAverageHousingScore/{housingId}")
    public ResponseEntity<?> getAverageHousingScore(@PathVariable Long housingId) {
        try {
            Float averageScore = feedbackService.getAnAverageHousingScore(housingId);
            return ResponseEntity.ok(averageScore);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Housing with id: " + housingId + " not found!");
        }
    }

    @GetMapping("/getAllFeedbecksByHousingId/{housingId}")
    public ResponseEntity<?> getAllFeedbacksByHousingId(@PathVariable Long housingId) {
        try {
            HousingDTO housing = housingService.getHousingById(housingId);
            if (housing == null) {
                throw new HousingNotFoundException(housingId.toString());
            }
            List<FeedbackDTO> feedbacks = feedbackService.getFeedbacks(housingId);
            return ResponseEntity.ok(feedbacks);
        } catch (EmptyFeedbacksException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review from housingId is empty.");
        } catch (HousingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Housing not found with ID " + housingId + ".");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}

