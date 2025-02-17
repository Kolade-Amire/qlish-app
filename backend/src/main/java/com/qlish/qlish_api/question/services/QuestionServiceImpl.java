package com.qlish.qlish_api.question.services;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.exceptions.EntityNotFoundException;
import com.qlish.qlish_api.exceptions.QuestionsRetrievalException;
import com.qlish.qlish_api.question.dtos.QuestionDto;
import com.qlish.qlish_api.question.dtos.QuestionRequest;
import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.question.repositories.QuestionRepository;
import com.qlish.qlish_api.test.enums.Subject;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository repository;


    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDto> getBySubject(String subject, int pageNum, int pageSize) {
        Subject sub = Subject.fromName(subject);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Question> questions;
        try {
            questions = repository.findAllBySubject(sub, pageable);
        } catch (Exception e) {
            LOGGER.error("error getting questions for subject {}:", sub.name(), e);
            throw new QuestionsRetrievalException("failed to retrieve questions, try again.");
        }
        return questions.map(QuestionDto::fromEntity);
    }


    @Override
    @Transactional
    public QuestionDto updateQuestion(String id, QuestionRequest request) {
        var _id = AppConstants.idFromString(id);

        Subject subject = Subject.fromName(request.getSubject());
        DifficultyLevel level = DifficultyLevel.fromName(request.getLevel());
        String topic = validateTopic(subject, level, request.getTopic());

        Question question = getById(_id);

        question.setQuestionText(request.getQuestionText());
        question.setOptions(request.getOptions());
        question.setAnswer(request.getAnswer());
        question.setSubject(subject);
        question.setTopic(topic);
        question.setLevel(level);

        var savedQuestion = saveQuestion(question);
        return QuestionDto.fromEntity(savedQuestion);

    }


    @Override
    @Transactional
    public void deleteQuestion(String id) {
        var _id = AppConstants.idFromString(id);
        var question = getById(_id);
        try {
            repository.delete(question);
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting question", e);
            throw new CustomQlishException("failed to delete question");
        }
    }


    @Override
    @Transactional
    public QuestionDto createQuestion(QuestionRequest request) {
        Subject subject = Subject.fromName(request.getSubject());
        DifficultyLevel level = DifficultyLevel.fromName(request.getLevel());
        String topic = validateTopic(subject, level, request.getTopic());


        Question question = Question.builder()
                .subject(subject)
                .questionText(request.getQuestionText())
                .options(request.getOptions())
                .answer(request.getAnswer())
                .topic(topic)
                .level(level)
                .build();

        var savedQuestion = saveQuestion(question);

        return QuestionDto.fromEntity(savedQuestion);

    }

    private String validateTopic(Subject subject, DifficultyLevel level, String topic) {

        Set<String> validTopics = subject.getTopicsForLevel(level);
        String topicsString = String.join(", ", validTopics);

        if (!validTopics.contains(topic.toLowerCase())) {
            throw new IllegalArgumentException(String.format("invalid topic %s for difficulty level %s in subject %s, valid topics for level are %s", topic, level, subject, topicsString));
        }

        return topic.toLowerCase();
    }


    @Override
    @Transactional(readOnly = true)
    public QuestionDto retrieveQuestion(String id) {
        Question question;
        ObjectId _id = AppConstants.idFromString(id);
        try {
            question = getById(_id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error("error occurred while retrieving question {}:", id, e);
            throw new CustomQlishException("failed to retrieve question");
        }
        return QuestionDto.fromEntity(question);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDto> getByTopic(String topic, int pageNum, int pageSize) {
       boolean topicExists = Arrays.stream(Subject.values())
               .anyMatch(subject -> subject.getTopics().values()
                       .stream()
                       .anyMatch(topicsSet -> topicsSet.contains(topic.toLowerCase())
               ));
       if(!topicExists){
           throw new IllegalArgumentException(String.format("topic %s not found for any subject!", topic));
       }
       Pageable pageable = PageRequest.of(pageNum, pageSize);
       Page<Question> questions;
        try {
            questions = repository.findAllByTopic(topic.toLowerCase(), pageable);
        } catch (Exception e) {
            LOGGER.error("error getting questions for topic {}:", topic, e);
            throw new QuestionsRetrievalException("failed to retrieve questions, try again.");
        }
       return questions.map(QuestionDto::fromEntity);
    }

    @Override
    @Transactional
    public Question saveQuestion(Question question) {
        try {
            return repository.save(question);
        } catch (MongoWriteException e) {
            LOGGER.error("Mongo write error: {}", e.getMessage(), e);
            throw new CustomQlishException("failed to save question");
        } catch (MongoTimeoutException e) {
            LOGGER.error("Mongo timeout error: {}", e.getMessage(), e);
            throw new CustomQlishException("failed to save question");
        } catch (DataAccessException e) {
            LOGGER.error("Data access error: {}", e.getMessage(), e);
            throw new CustomQlishException("failed to save question");
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new CustomQlishException("failed to save question");
        }

    }

    private Question getById(ObjectId id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("question not found")
        );
    }
}
