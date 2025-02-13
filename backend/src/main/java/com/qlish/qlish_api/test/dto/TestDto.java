package com.qlish.qlish_api.test.dto;

import com.qlish.qlish_api.test.enums.TestStatus;
import com.qlish.qlish_api.test.enums.Subject;
import com.qlish.qlish_api.test.model.TestQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class TestDto {
    @EqualsAndHashCode.Include
    private String id;
    private ObjectId userId;
    private Subject subject;
    private List<TestQuestion> questions;
    private LocalDateTime startedAt;
    private TestStatus testStatus;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
    private int pointEarned;
}
