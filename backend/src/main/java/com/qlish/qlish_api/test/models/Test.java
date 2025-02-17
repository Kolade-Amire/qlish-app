package com.qlish.qlish_api.test.models;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.test.enums.Subject;
import com.qlish.qlish_api.test.enums.TestStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tests")
public class Test {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private Subject subject;
    private DifficultyLevel difficultyLevel;
    private LocalDateTime startedAt;
    private int totalQuestions;
    private Integer totalCorrect;
    private Integer totalIncorrect;
    private Integer scorePercentage;
    private Integer pointsEarned;
    private TestStatus testStatus;
    private List<TestQuestion> questions;
    private String feedback;
}
