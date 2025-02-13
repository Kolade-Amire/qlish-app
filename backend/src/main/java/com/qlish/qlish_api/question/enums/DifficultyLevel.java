package com.qlish.qlish_api.question.enums;

import lombok.Getter;

@Getter
public enum DifficultyLevel {
    ADVANCED,
    INTERMEDIATE,
    ELEMENTARY;

    public static DifficultyLevel fromLevelName(String levelName) {
        for (DifficultyLevel level : DifficultyLevel.values()) {
            if (level.name().equalsIgnoreCase(levelName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("invalid difficulty level");
    }
}
