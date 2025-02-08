package com.qlish.qlish_api.question.dto;

import com.qlish.qlish_api.test.enums.TestSubject;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class QuestionDto {
    private String id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private TestSubject subject;
    private Map<String, String> modifiers;

}
