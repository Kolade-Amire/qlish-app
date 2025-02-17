package com.qlish.qlish_api.question.services;

import com.qlish.qlish_api.question.dtos.QuestionDto;
import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.question.dtos.QuestionRequest;
import org.springframework.data.domain.Page;

public interface QuestionService {

    QuestionDto retrieveQuestion(String id);

    Question saveQuestion(Question question);

    QuestionDto createQuestion(QuestionRequest request);

    QuestionDto updateQuestion(String id, QuestionRequest request);

    void deleteQuestion(String id);

    Page<QuestionDto> getByTopic(String topic, int pageNum, int pageSize);

    Page<QuestionDto> getBySubject(String subject, int pageNum, int pageSize);

}
