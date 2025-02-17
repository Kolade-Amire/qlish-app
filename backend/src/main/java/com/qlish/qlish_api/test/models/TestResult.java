package com.qlish.qlish_api.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TestResult {
    private int totalQuestions;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
    private int pointsEarned;
}
