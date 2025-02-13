package com.qlish.qlish_api.test.dto;

import com.qlish.qlish_api.question.model.Question;
import com.qlish.qlish_api.test.model.TestQuestion;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<TestQuestion> questions){
        return questions.stream()
                .map(TestQuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<TestQuestion> mapQuestionListToSavedTestQuestionDto(List<Question> questions) {
        return questions.stream().map(
                TestQuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
    }

    public static TestQuestionDto mapQuestionToTestViewDto(TestQuestion question){

        return TestQuestionDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static TestQuestion mapQuestionToSavedTestQuestionDto(Question question){
        return TestQuestion.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .modifiers(question.getAttributes())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
