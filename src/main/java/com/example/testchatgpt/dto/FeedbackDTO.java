package com.example.testchatgpt.dto;

import com.example.testchatgpt.model.Feedback;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class FeedbackDTO {
    private Long housingId;
    private Long userId;
    private String comment;
    private byte countStars;

    public static FeedbackDTO convertToDTO(Feedback feedback){
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setHousingId(feedback.getHousingId().getId());
        feedbackDTO.setUserId(feedback.getUserId().getId());
        feedbackDTO.setComment(feedback.getComment());
        feedbackDTO.setCountStars(feedback.getCountStars());
        return feedbackDTO;
    }

    public static Feedback convertToFeedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        Housing housing = new Housing();
        housing.setId(feedbackDTO.getHousingId());
        feedback.setHousingId(housing);
        User user = new User();
        user.setId(feedbackDTO.getUserId());
        feedback.setUserId(user);
        feedback.setComment(feedbackDTO.getComment());
        feedback.setCountStars(feedbackDTO.getCountStars());
        return feedback;
    }
}
