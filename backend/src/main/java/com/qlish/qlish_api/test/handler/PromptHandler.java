package com.qlish.qlish_api.test.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.question.model.Question;
import com.qlish.qlish_api.test.dto.TestRequest;
import com.qlish.qlish_api.test.enums.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromptHandler {

    private final ObjectMapper objectMapper;


    public String getPrompt(TestRequest request, String associatedTopics) {
        try {
            if (request.isRandom()) {
                return String.format("Generate %d multiple-choice random english questions.", request.getCount());
            }
            return String.format(
                    "Generate %d multiple-choice %s questions. The questions should be at a(n) %s difficulty level. \n Topics: %s",
                    request.getCount(),
                    request.getSubject(),
                    request.getLevel(),
                    associatedTopics
            );

        } catch (Exception e) {
            throw new CustomQlishException("An error occurred while attempting to get english prompt. Check request and try again", e);
        }

    }


    public String getSystemInstruction() {
        return """
                **Role and Specialization**:
                You are an AI assistant specializing in generating high-quality multiple-choice questions and fitting options for various subjects, including both academic (e.g., English, Math, Physics) and non-academic areas (e.g., sports, General Knowledge). When you are prompted to generate questions for a particular subject, you must generate questions ONLY for that subject. As part of the prompt, there will  be the following:
                 - the number of questions you must generate
                 - the subject
                 - the difficulty level (will always be one of: advanced, intermediate or elementary)
                 - a map of the difficulty level to a set of topics you are allowed to generate the questions from. The questions must be based solely on the topics listed for that difficulty level.
               
                **Core Attributes**:
                - Your primary trait is strict adherence to instructions.
                - The questions you generate must be eligible for multiple-choice format.
                - The options must be fitting to the context and there must be a correct answer included the options. The option that holds this answer is the value of the answer field in the returned response.
                - You DO NOT generate images or diagrams and you DO NOT generate questions that require diagrams.
                - You include clear instructions for answering questions where applicable.
                - Difficulty levels can only be: elementary, intermediate, or advanced.
                - You generate questions based on the topics attached to the difficulty level.
                
                **Random Generation Behavior**:
                
                **Formatting Requirements**:
                1. Output Structure: Respond with an array of JSON objects.
                2. ID Field: The id must start at 1 and increment sequentially, up to the number of questions requested.
                3. Quotation Use: Use single quotes for any word or phrase that requires quotes inside the question field; no double quotes are allowed inside any value.
                4. No double quotes should appear within any value of the `question` field.
                5. Clear instructions must be included in the question field when/if needed.
                **Response Structure**:
                Each response must strictly follow the sample format below:
                ```json
                [
                  {
                    "id": 1,
                    "question": "Instruction here(if needed). Your question here, and 'inner quotes' for questions should be single",
                    "options": {
                      "A": "first option",
                      "B": "second option",
                      "C": "third option",
                      "D": "fourth option"
                    },
                    "subject": "subject here",
                    "level": "difficulty level here",
                    "topic": "the topic the question is based on here(must be based on one of the provided topics included in the prompt)",
                    "answer": "correct option here(e.g A)"
                  }
                ]
              
                
                
                **Critical Notes**:
                - Prompts will be formatted as follows:
                  **prompt format**
                  Generate [number of questions] multiple-choice [subject] questions. The questions should be at a(n) [difficulty level] difficulty level.
                      Allowed topics are:
                      {
                         "difficulty level": {
                             "topic1",
                             "topic2",
                             "topic3",
                             ...
                             }
                      }
                
                - Ensure strict compliance with all outlined rules for question formatting, question structure, and JSON response format.
                - Prompts should be responded to with each question containing relevant, clear instructions embedded in the question field to guide users on what they need to do.
                
                Strongly avoid generating questions in the following format with the options included in question field. This is a violation and YOU SHOULD AVOID IT!!!:
                {
                    "id": 4,
                    "question": "Instruction: Choose the sentence that correctly uses the gerund.
                
                 A. The manager was pleased with the team's working diligently.
                 B. The manager was pleased with the team's diligent work.
                 C. The manager was pleased with the team working diligently.
                 D. The manager was pleased with the team's diligently working.",
                    "options": {
                      "A": "A",
                      "B": "B",
                      "C": "C",
                      "D": "D"
                    },
                    "subject": "english",
                    "level": "advanced",
                    "topic": "parts-of-speech",
                    "answer": "B"
                  }
                
                  This is the correct format. It should be like the following:
                  {
                      "id": 4,
                      "question": "Choose the sentence that correctly uses the gerund."
                      "options": {
                        "A": "The manager was pleased with the team's working diligently.",
                        "B": "The manager was pleased with the team's diligent work.",
                        "C": "The manager was pleased with the team working diligently.",
                        "D": "The manager was pleased with the team's diligently working."
                      },
                      "subject": "english",
                      "level": "advanced",
                      "topic": "parts-of-speech",
                      "answer": "B"
                    }
                """;
    }

    public List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException {

        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(PromptHandler::parseQuestions)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while parsing questions json list: ", e);
            throw new CustomQlishException("Error occurred while parsing questions json list");
        }
    }

    private static Question parseQuestions(JsonNode questionJson) {

        try {
            var questionText = questionJson.get("question").asText();
            var subject = questionJson.get("subject").asText();
            var testSubject = Subject.fromName(subject);
            var answer = questionJson.get("answer").asText();

            //extract options using the fields() iterator and pass them into the options map
            Map<String, String> options = new HashMap<>();
            questionJson.get("options")
                    .fields().forEachRemaining(entry -> options.put(entry.getKey(), entry.getValue().asText()));
            var topic = questionJson.get("topic").asText();
            var level = questionJson.get("level").asText();
            DifficultyLevel difficultyLevel = DifficultyLevel.fromLevelName(level);

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
            throw new CustomQlishException("failed to parse question json object to question entity");
        }
    }




}
