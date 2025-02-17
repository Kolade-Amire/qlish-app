package com.qlish.qlish_api.test.models;

import com.qlish.qlish_api.question.models.Question;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestion {
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private String correctAnswer;
    private String topic;
    private String selectedOption;
    private boolean isAnswerCorrect;

    public static List<TestQuestion> fromEntityList(List<Question> questions) {
        return questions.stream().map(
                TestQuestion::fromEntity
        ).toList();
    }

    public static TestQuestion fromEntity(Question question){
        return TestQuestion.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .topic(question.getTopic())
                .build();
    }
}
