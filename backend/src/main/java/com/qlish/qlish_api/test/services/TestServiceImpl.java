package com.qlish.qlish_api.test.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.exceptions.EntityNotFoundException;
import com.qlish.qlish_api.exceptions.GenerativeAIException;
import com.qlish.qlish_api.exceptions.TestSubmissionException;
import com.qlish.qlish_api.generativeAI.GeminiAIClient;
import com.qlish.qlish_api.generativeAI.GenerativeAIService;
import com.qlish.qlish_api.generativeAI.QuestionParser;
import com.qlish.qlish_api.leaderboard.LeaderboardEntry;
import com.qlish.qlish_api.leaderboard.LeaderboardService;
import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.question.repositories.QuestionRepository;
import com.qlish.qlish_api.test.dtos.*;
import com.qlish.qlish_api.test.enums.TestStatus;
import com.qlish.qlish_api.test.models.Test;
import com.qlish.qlish_api.test.models.TestQuestion;
import com.qlish.qlish_api.test.models.TestResult;
import com.qlish.qlish_api.test.repositories.TestRepository;
import com.qlish.qlish_api.user.model.User;
import com.qlish.qlish_api.user.services.UserService;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    private final GeminiAIClient geminiAIClient;
    private final UserService userService;
    private final GenerativeAIService generativeAIService;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final LeaderboardService leaderboardService;
    private final QuestionParser parser;


    @Override
    @Transactional(readOnly = true)
    public Test getTestById(ObjectId id) {
        return testRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found with ID: " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TestDto retrieveTest(String id) {
        var _id = AppConstants.idFromString(id);
        var test = getTestById(_id);
        return TestDto.fromEntity(test);
    }


    @Override
    @Transactional
    public ObjectId saveTest(Test test) {
        try {
            return testRepository.save(test).getId();
        } catch (MongoWriteException e) {
            logger.error("Mongo write error: {}", e.getMessage(), e);
            throw new CustomQlishException("Error saving test. Please try again");
        } catch (MongoTimeoutException e) {
            logger.error("Mongo timeout error: {}", e.getMessage(), e);
            throw new CustomQlishException("Error saving test. Please try again");
        } catch (DataAccessException e) {
            logger.error("Data access error: {}", e.getMessage(), e);
            throw new CustomQlishException("Error saving test. Please try again");
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new CustomQlishException("Error saving test. Please try again");
        }
    }

    @Override
    @Transactional
    public TestResponse createTest(TestRequest request) {

        List<Question> generatedQuestions;

        try {
            generatedQuestions = generateQuestions(request);
        } catch (GenerativeAIException e) {
            logger.error("Error occurred while generating test questions", e);
            throw new CustomQlishException("Unable to create test. Try again later");
        }


        List<TestQuestion> testQuestions = TestQuestion.fromEntityList(generatedQuestions);
        var userId = AppConstants.idFromString(request.getUserId());
        var newTest = Test.builder()
                .userId(userId)
                .subject(request.getSubject())
                .difficultyLevel(request.getLevel())
                .startedAt(LocalDateTime.now())
                .totalQuestions(request.getCount())
                .testStatus(TestStatus.CREATED)
                .questions(testQuestions)
                .build();

        return TestResponse.builder()
                .testId(saveTest(newTest).toHexString())
                .message("Created Successfully!")
                .build();

    }

    @Override
    @Transactional
    public List<Question> generateQuestions(TestRequest request) throws GenerativeAIException {

        var prompt = generativeAIService.getPrompt(request);
        var systemInstruction = generativeAIService.getSystemInstruction();

        try {
            String questions = geminiAIClient.generateQuestions(prompt, systemInstruction);
            String _questions = cleanResponse(questions);
            List<Question> questionsList = parser.parseResponse(_questions);
            return saveGeneratedQuestions(questionsList);
        } catch (GenerativeAIException | JsonProcessingException e) {
            logger.error("Error generating question", e);
            throw new CustomQlishException("An unexpected error occurred. Please try again later");
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            throw new CustomQlishException("An unexpected error occurred. Please try again later");
        }
    }

    private String cleanResponse(String jsonResponse) throws JsonProcessingException {
        //unescape the questions list json string and clean it
        return StringEscapeUtils.unescapeJson(jsonResponse)
                .replaceAll("^```json|```$", "")
                .trim();
    }

    @Transactional
    public List<Question> saveGeneratedQuestions(List<Question> generatedQuestions) {

        try {
            return questionRepository.saveAll(generatedQuestions);
        } catch (MongoTimeoutException e) {
            logger.error("Mongo timeout error occurred while saving test questions", e);
            throw new CustomQlishException("Error saving test questions");
        } catch (MongoBulkWriteException e) {
            logger.error("Mongo Write error", e);
            throw new CustomQlishException("Error saving test questions");
        } catch (Exception e) {
            logger.error("An unexpected error occurred while trying to save test questions to database", e);
            throw new CustomQlishException("Error saving test questions");
        }
    }

    @Override
    @Transactional
    public void deleteTest(String testId) {
        var id = AppConstants.idFromString(testId);
        try {
            var test = getTestById(id);
            testRepository.delete(test);
        } catch (Exception e) {
            logger.error("Error deleting test", e);
            throw new CustomQlishException("An error occurred while trying to delete test");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestQuestionResponse> getTestQuestions(String testId) {
        var id = AppConstants.idFromString(testId);
        var test = getTestById(id);
        var questions = test.getQuestions();

        List<TestQuestionResponse> _questions = TestQuestionResponse.fromTestQuestionList(questions);

        test.setTestStatus(TestStatus.STARTED);

        return _questions;
    }

    @Override
    @Transactional
    public TestResponse submitTest(TestSubmissionRequest request) {
        var id = AppConstants.idFromString(request.getTestId());
        try {
            var test = getTestById(id);
            List<TestQuestion> testQuestions = test.getQuestions();

            var answers = request.getSubmissions();

            mapSubmissionToTest(answers, testQuestions);

            gradeTest(test);

            // Mark the test as completed
            test.setTestStatus(TestStatus.COMPLETED);

            saveTest(test);

            User user = userService.findUserById(test.getUserId());

            var leaderboardEntry = LeaderboardEntry.builder()
                    .points(test.getPointsEarned().longValue())
                    .profileName(user.getProfileName())
                    .build();

            leaderboardService.updateDailyLeaderboard(leaderboardEntry);
            leaderboardService.updateAllTimeLeaderboard(leaderboardEntry);

            return TestResponse.builder()
                    .testId(test.getId().toHexString())
                    .message("Submitted Successfully!")
                    .build();
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw new TestSubmissionException(AppConstants.TEST_SUBMISSION_ERROR);
        }

    }


    private void mapSubmissionToTest(Map<String, String> submissions, List<TestQuestion> testQuestions) {

        submissions.forEach((key, value) -> {
            TestQuestion question = testQuestions.stream()
                    .filter(q ->
                            q.getId().equals(
                                    new ObjectId(key)
                            )
                    )
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(AppConstants.QUESTION_NOT_FOUND));

            question.setSelectedOption(value);
            question.setAnswerCorrect(
                    value.equalsIgnoreCase(question.getCorrectAnswer())
            );
        });


    }

    @Transactional
    public void gradeTest(Test test) {

        try {
            TestResult result = TestGradingStrategy.calculateTestScore().apply(test.getQuestions());
            Integer testPoints = PointsGradingStrategy.calculatePoints().apply(result.getScorePercentage(), test.getDifficultyLevel());

            if (isTestResultValid(result)) {
                test.setPointsEarned(testPoints);
                test.setScorePercentage(result.getScorePercentage());
                test.setTotalCorrect(result.getTotalCorrectAnswers());
                test.setTotalIncorrect(result.getTotalIncorrectAnswers());
                result.setPointsEarned(testPoints);
                test.setTestStatus(TestStatus.GRADED);
            }
            //update user's all-time points
            ObjectId userId = test.getUserId();
            userService.updateUserAllTimePoints(userId, testPoints);
        } catch (Exception e) {
            logger.error("An error occurred during while grading test(id={})", test.getId().toHexString(), e);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public TestResult getTestResult(String id) {
        var _id = AppConstants.idFromString(id);
        Test test = getTestById(_id);
        return TestResult.builder()
                .totalQuestions(test.getTotalQuestions())
                .totalCorrectAnswers(test.getTotalCorrect())
                .scorePercentage(test.getScorePercentage())
                .pointsEarned(test.getPointsEarned())
                .build();
    }

    private boolean isTestResultValid(TestResult result) {
        return result.getScorePercentage() >= 0 && result.getPointsEarned() >= 0;
    }


}
