package com.example.testchatgpt.Service;

import com.example.testchatgpt.dto.FeedbackDTO;
import com.example.testchatgpt.model.Feedback;

import java.util.List;

public interface FeedbackService {

    Feedback createFeedback(Feedback feedback);

    List<FeedbackDTO> getFeedbacks(Long housingId);

    Float getAnAverageHousingScore(Long housingId);

}
