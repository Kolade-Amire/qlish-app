package com.qlish.qlish_api.test.dtos;

import com.qlish.qlish_api.test.enums.TestStatus;
import com.qlish.qlish_api.test.enums.Subject;
import com.qlish.qlish_api.test.models.Test;
import com.qlish.qlish_api.test.models.TestQuestion;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class TestDto {
    @EqualsAndHashCode.Include
    private String id;
    private String userId;
    private Subject subject;
    private List<TestQuestion> questions;
    private LocalDateTime startedAt;
    private TestStatus testStatus;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
    private int pointEarned;


    public static TestDto fromEntity(Test test) {
        return TestDto.builder()
                .id(test.getId().toHexString())
                .userId(test.getUserId().toHexString())
                .questions(test.getQuestions())
                .subject(test.getSubject())
                .startedAt(test.getStartedAt())
                .totalQuestionCount(test.getTotalQuestions())
                .totalCorrectAnswers(test.getTotalCorrect())
                .totalIncorrectAnswers(test.getTotalIncorrect())
                .pointEarned(test.getPointsEarned())
                .scorePercentage(test.getScorePercentage())
                .build();
    }
}
