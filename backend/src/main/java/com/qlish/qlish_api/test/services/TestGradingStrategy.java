package com.qlish.qlish_api.test.services;

import com.qlish.qlish_api.test.models.TestQuestion;
import com.qlish.qlish_api.test.models.TestResult;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface TestGradingStrategy extends Function<List<TestQuestion>, TestResult> {

    static TestGradingStrategy calculateTestScore() {
        return testQuestions -> {
            int correctAnswers = (int) testQuestions.stream().filter(TestQuestion::isAnswerCorrect).count();
            int incorrectAnswers = testQuestions.size() - correctAnswers;
            int scorePercentage = (int) Math.round(100.0 * ((double) correctAnswers / testQuestions.size()));
            return TestResult.builder()
                    .totalQuestions(testQuestions.size())
                    .totalCorrectAnswers(correctAnswers)
                    .totalIncorrectAnswers(incorrectAnswers)
                    .scorePercentage(scorePercentage)
                    .build();
        };
    }
}
