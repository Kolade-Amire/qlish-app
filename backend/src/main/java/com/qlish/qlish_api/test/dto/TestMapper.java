package com.qlish.qlish_api.test.dto;

import com.qlish.qlish_api.test.model.Test;

public class TestMapper {
    public static TestDto mapTestToDto(Test test) {
        var testDetails = test.getTestDetails();
        return TestDto.builder()
                .id(test.getId().toHexString())
                .userId(testDetails.getUserId())
                .questions(test.getQuestions())
                .testSubject(testDetails.getTestSubject())
                .startedAt(testDetails.getStartedAt())
                .totalQuestionCount(testDetails.getTotalQuestions())
                .totalCorrectAnswers(testDetails.getTotalCorrect())
                .totalIncorrectAnswers(testDetails.getTotalIncorrect())
                .pointEarned(testDetails.getPointsEarned())
                .scorePercentage(testDetails.getScorePercentage())
                .build();
    }
}