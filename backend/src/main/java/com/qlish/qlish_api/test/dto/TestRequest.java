package com.qlish.qlish_api.test.dto;

import com.qlish.qlish_api.test.enums.TestSubject;
import com.qlish.qlish_api.test.enums.TestType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@AllArgsConstructor
@Getter
public class TestRequest {
    private String userId;
    private TestSubject subject;
    private TestType testType;
    private Map<String, String> modifiers;
    private int count;
    private boolean isRandom;
}
