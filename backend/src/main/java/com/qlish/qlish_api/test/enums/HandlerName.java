package com.qlish.qlish_api.test.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HandlerName {
    ENGLISH("englishHandler"),
    MATH("mathHandler"),
    PHYSICS("physicsHandler"),
    CHEMISTRY("chemistryHandler"),
    HISTORY("historyHandler"),
    GENERAL_QUIZ("quizHandler");

    private final String handlerName;

    public static String getHandlerNameBySubject(TestSubject subjectName) {
        return Arrays.stream(HandlerName.values())
                .filter(handlerEnumValue -> handlerEnumValue.name().equalsIgnoreCase(subjectName.name()))
                .findFirst()
                .map(HandlerName::getHandlerName)
                .orElseThrow(() -> new IllegalArgumentException("No handler found for subject: " + subjectName.name()));
    }
}
