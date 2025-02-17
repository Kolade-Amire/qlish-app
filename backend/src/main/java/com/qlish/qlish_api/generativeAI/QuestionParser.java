package com.qlish.qlish_api.generativeAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.test.enums.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Component
public class QuestionParser {
    private final ObjectMapper objectMapper;

    public List<Question> parseResponse(String jsonResponse) throws JsonProcessingException {

        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(this::toEntity)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while parsing questions json to entity list ", e);
            throw new CustomQlishException("Error occurred while parsing questions json to entity list");
        }
    }

    private Question toEntity(JsonNode questionJson) {

        try {
            var questionText = questionJson.get("question").asText();
            var subject = questionJson.get("subject").asText();
            Subject testSubject = Subject.fromName(subject);
            var answer = questionJson.get("answer").asText();

            //extract options using the fields() iterator and pass them into the options map
            Map<String, String> options = new HashMap<>();
            questionJson.get("options")
                    .fields().forEachRemaining(entry -> options.put(entry.getKey(), entry.getValue().asText()));
            var topic = questionJson.get("topic").asText();
            var level = questionJson.get("level").asText();
            DifficultyLevel difficultyLevel = DifficultyLevel.fromName(level);

            return Question.builder()
                    .questionText(questionText)
                    .options(options)
                    .subject(testSubject)
                    .topic(topic)
                    .level(difficultyLevel)
                    .answer(answer)
                    .build();

        } catch (Exception e) {
            log.error("Failed to parse question json object to question entity: ", e);
            throw new CustomQlishException("error parsing json object to question entity");
        }
    }
}
