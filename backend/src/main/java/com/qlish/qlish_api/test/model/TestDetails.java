package com.qlish.qlish_api.test.model;

import com.qlish.qlish_api.test.enums.DifficultyLevel;
import com.qlish.qlish_api.test.enums.TestSubject;
import com.qlish.qlish_api.test.enums.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class TestDetails {
    private ObjectId userId;
    private TestType testType;
    private TestSubject testSubject;
    private DifficultyLevel difficultyLevel;
    private LocalDateTime startedAt;
    private int totalQuestions;
    private Integer totalCorrect; 
    private Integer totalIncorrect;
    private Integer scorePercentage;
    private Integer pointsEarned;
    private boolean isCompleted;
}
