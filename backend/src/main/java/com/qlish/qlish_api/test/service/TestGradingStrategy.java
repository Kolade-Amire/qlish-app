package com.qlish.qlish_api.test.service;

import com.qlish.qlish_api.test.model.TestQuestion;
import com.qlish.qlish_api.test.model.TestResult;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface TestGradingStrategy extends Function<List<TestQuestion>, TestResult> {

    static TestGradingStrategy calculateTestScore() {
        return testQuestions -> {
            var correctAnswers = (int) testQuestions.stream().filter(TestQuestion::isAnswerCorrect).count();
            var incorrectAnswers = testQuestions.size() - correctAnswers;
            int scorePercentage = 100 * (correctAnswers / testQuestions.size());
            return TestResult.builder()
                    .totalQuestions(testQuestions.size())
                    .totalCorrectAnswers(correctAnswers)
                    .totalIncorrectAnswers(incorrectAnswers)
                    .scorePercentage(scorePercentage)
                    .build();
        };
    }
}
