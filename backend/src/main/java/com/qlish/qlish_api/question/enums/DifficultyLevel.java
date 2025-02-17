package com.qlish.qlish_api.question.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DifficultyLevel {
    ADVANCED,
    INTERMEDIATE,
    ELEMENTARY;

    public static DifficultyLevel fromName(String level) {
        return Arrays.stream(DifficultyLevel.values())
                .filter(lvl -> lvl.name().equalsIgnoreCase(level))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid difficulty level " + level));


    }
}
