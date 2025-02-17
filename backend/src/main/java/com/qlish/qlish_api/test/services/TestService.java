package com.qlish.qlish_api.test.services;

import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.test.dtos.*;
import com.qlish.qlish_api.exceptions.GenerativeAIException;
import com.qlish.qlish_api.test.models.Test;
import com.qlish.qlish_api.test.models.TestResult;
import org.bson.types.ObjectId;

import java.util.List;

public interface TestService {

    TestDto retrieveTest(String id);

    ObjectId saveTest(Test testDto);

    TestResponse createTest (TestRequest request) throws GenerativeAIException;

    List<Question> generateQuestions(TestRequest request) throws GenerativeAIException;

    List<TestQuestionResponse> getTestQuestions(String id);

    Test getTestById(ObjectId id);

    TestResponse submitTest(TestSubmissionRequest request);

    TestResult getTestResult(String id);

    void deleteTest(String id);
}
