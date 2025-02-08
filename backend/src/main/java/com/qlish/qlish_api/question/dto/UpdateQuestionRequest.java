package com.qlish.qlish_api.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class UpdateQuestionRequest {
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private Map<String, String> modifiers;
}
