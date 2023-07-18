package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.FeedbackService;
import com.example.testchatgpt.dto.FeedbackDTO;
import com.example.testchatgpt.errors.EmptyFeedbacksException;
import com.example.testchatgpt.errors.HousingNotFoundException;
import com.example.testchatgpt.model.Feedback;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.repository.FeedBackRepository;
import com.example.testchatgpt.repository.HousingRepository;
import com.example.testchatgpt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final HousingRepository housingRepository;
    private final FeedBackRepository feedBackRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackServiceImpl(HousingRepository housingRepository, FeedBackRepository feedBackRepository, UserRepository userRepository) {
        this.housingRepository = housingRepository;
        this.feedBackRepository = feedBackRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        if (!feedback.getComment().equals("")) {
            feedBackRepository.save(feedback);
        } else throw new RuntimeException("Feedback cannot be empty!");
        return feedback;
    }

    @Override
    public List<FeedbackDTO> getFeedbacks(Long housingId) {

        Optional<Housing> housing = housingRepository.findById(housingId);
        if (housing.isEmpty()) {
            throw new HousingNotFoundException(String.valueOf(housingId));
        }

        List<Feedback> feedbacks = feedBackRepository.findByHousingId(housing.get());

        if (!feedbacks.isEmpty()) {
            return feedbacks.stream()
                    .map(FeedbackDTO::convertToDTO).toList();
        } else {
            throw new EmptyFeedbacksException("Review from housingId is empty!");
        }
    }

    @Override
    public Float getAnAverageHousingScore(Long housingId) {
        Optional<Housing> housing = housingRepository.findById(housingId);
        List<Feedback> feedbacks = feedBackRepository.findByHousingId(housing.get());
        if (feedbacks.isEmpty()) {
            return 0.0f;
        }
        int totalStars = feedbacks.stream().mapToInt(Feedback::getCountStars).sum();
        return (float) totalStars / feedbacks.size();
    }
}
