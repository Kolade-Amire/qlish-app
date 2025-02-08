package com.qlish.qlish_api.test.service;

import com.qlish.qlish_api.question.model.Question;
import com.qlish.qlish_api.test.dto.TestQuestionDto;
import com.qlish.qlish_api.test.dto.TestDto;
import com.qlish.qlish_api.exception.GenerativeAIException;
import com.qlish.qlish_api.test.dto.TestRequest;
import com.qlish.qlish_api.test.dto.TestSubmissionRequest;
import com.qlish.qlish_api.test.model.Test;
import com.qlish.qlish_api.test.model.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    TestDto getTestForView(String id);

    ObjectId saveTest(Test testDto);

    String createTest (TestRequest request) throws GenerativeAIException;

    List<Question> generateQuestions(TestRequest request) throws GenerativeAIException;

    Page<TestQuestionDto> getTestQuestions(String id, Pageable pageable);

    Test getTestById(ObjectId id);

    String submitTest(TestSubmissionRequest request);

    TestResult getTestResult(String id);

    void deleteTest(String id);
}
