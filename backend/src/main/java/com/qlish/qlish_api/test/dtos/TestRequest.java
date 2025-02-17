package com.qlish.qlish_api.test.dtos;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.test.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TestRequest {
    @NotBlank
    private String userId;
    @NotNull
    private Subject subject;
    @NotNull
    private DifficultyLevel level;
    @NotNull
    private int count;
    private boolean random;
}
