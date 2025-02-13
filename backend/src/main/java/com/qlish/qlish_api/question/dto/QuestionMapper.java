package com.qlish.qlish_api.question.dto;

import com.qlish.qlish_api.question.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class QuestionMapper {


    public static QuestionDto mapQuestionToQuestionDto(Question question) {

        return QuestionDto.builder()
                .id(question.getId().toHexString())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .attributes(question.getAttributes())
                .subject(question.getSubject())
                .build();
    }


    public static Page<QuestionDto> mapToQuestionDtoPage(Page<Question> questions, Pageable pageable) {
        List<QuestionDto> questionDtos = questions.stream().map(
                QuestionMapper::mapQuestionToQuestionDto
        ).toList();

        return new PageImpl<>(questionDtos, pageable, questions.getTotalElements());
    }


}

