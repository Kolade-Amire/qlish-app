package com.qlish.qlish_api.test.dtos;


import com.qlish.qlish_api.test.models.TestQuestion;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class TestQuestionResponse {
    private String id;
    private String questionText;
    private Map<String, String> options;


    public static List<TestQuestionResponse> fromTestQuestionList(List<TestQuestion> questions){
        return questions.stream()
                .map(TestQuestionResponse::fromTestQuestion)
                .toList();
    }



    public static TestQuestionResponse fromTestQuestion(TestQuestion question){

        return TestQuestionResponse.builder()
                .id(question.getId().toHexString())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }



}
