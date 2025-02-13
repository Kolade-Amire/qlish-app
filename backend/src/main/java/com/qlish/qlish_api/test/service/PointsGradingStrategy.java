package com.qlish.qlish_api.test.service;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.util.PointsSystem;

import java.util.function.BiFunction;

@FunctionalInterface
public interface PointsGradingStrategy extends BiFunction<Integer, DifficultyLevel, Integer> {

    static PointsGradingStrategy calculatePoints(){
        return PointsSystem::getTotalPoints;
    }
}
