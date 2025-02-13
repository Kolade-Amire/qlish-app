package com.qlish.qlish_api.test.dto;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.test.enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TestRequest {
    private String userId;
    private Subject subject;
    private DifficultyLevel level;
    private int count;
    private boolean random;
}
