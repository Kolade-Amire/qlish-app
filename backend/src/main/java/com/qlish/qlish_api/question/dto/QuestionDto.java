package com.qlish.qlish_api.question.dto;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.question.model.Question;
import com.qlish.qlish_api.test.enums.Subject;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class QuestionDto {
    private String id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private Subject subject;
    private String topic;
    private DifficultyLevel difficultyLevel;

    public static QuestionDto fromEntity(Question question){
        return QuestionDto.builder()
                .id(question.getId().toHexString())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .topic(question.getTopic())
                .difficultyLevel(question.getLevel())
                .subject(question.getSubject())
                .build();
    }

    public static Page<QuestionDto> fromPage(Page<Question> questions){
        return questions.map(QuestionDto::fromEntity);
    }

}
