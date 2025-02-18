package com.qlish.qlish_api.question.dtos;

import com.qlish.qlish_api.validation.ValidOption;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuestionRequest {
    @NotBlank
    private String questionText;
    @NotEmpty
    private Map<@ValidOption String, String> options;
    @NotBlank
    @ValidOption
    private String answer;
    @NotBlank
    private String subject;
    @NotBlank
    private String topic;
    @NotBlank
    private String level;
}