package com.qlish.qlish_api.generativeAI;

import com.google.common.collect.ImmutableList;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.qlish.qlish_api.exceptions.GenerativeAIException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GeminiAIClient {

    private static final Logger logger = LoggerFactory.getLogger(GeminiAIClient.class);

    private final Client client;


    public String generateQuestions(String prompt, String systemInstruction) throws GenerativeAIException {

        Content sysInstructions =
                Content.builder()
                        .parts(ImmutableList.of(Part.builder().text(systemInstruction).build()))
                        .build();

        GenerateContentConfig generateContentConfig = GenerateContentConfig.builder()
                .systemInstruction(sysInstructions)
                .build();

        try {
            GenerateContentResponse response = client.models.generateContent("gemini-2.0-flash", prompt, generateContentConfig);
            return response.text().replaceAll("^```json|```$", "").trim();
        } catch (IOException | HttpException e) {
            logger.error("an error occurred while generating question", e);
            throw new GenerativeAIException("an error occurred while generating question");
        }

    }






}
