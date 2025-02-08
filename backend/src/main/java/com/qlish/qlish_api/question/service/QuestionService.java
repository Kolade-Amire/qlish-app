package com.qlish.qlish_api.question.service;

import com.qlish.qlish_api.question.dto.QuestionDto;
import com.qlish.qlish_api.question.model.Question;
import com.qlish.qlish_api.question.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.question.dto.NewQuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page<QuestionDto> getQuestionsBySubjectAndCriteria(AdminQuestionViewRequest request, Pageable pageable);

     QuestionDto updateQuestion(QuestionDto request);

     QuestionDto getQuestion(String id);

     void deleteQuestion(String id);

     Question saveQuestion(Question question);

     QuestionDto addNewQuestion(NewQuestionRequest request);
}
