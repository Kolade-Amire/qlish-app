package com.qlish.qlish_api.util;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PointsSystem {

    private static int calculatePoints(int scorePercentage) {
        Map<Integer, Integer> pointsMapping = Map.of(
                0, 5,
                10, 10,
                20, 15,
                30, 20,
                40, 30,
                50, 40,
                60, 50,
                70, 60,
                80, 70,
                90, 80
        );

        for (Map.Entry<Integer, Integer> entry : pointsMapping.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (scorePercentage >= key && scorePercentage < key + 10) {
                return value;
            }
        }
        log.error("Invalid score percentage: {}", scorePercentage);
         throw new IllegalArgumentException("Invalid Score Percentage!");

}

public static int getTotalPoints(int scorePercentage, DifficultyLevel difficultyLevel) {
    var gradedPoints = calculatePoints(scorePercentage);
    Map<DifficultyLevel, Integer> multiplierMap = Map.of(
            DifficultyLevel.EASY, 1,
            DifficultyLevel.MEDIUM, 2,
            DifficultyLevel.HARD, 4
    );
    return (multiplierMap.get(difficultyLevel) * gradedPoints);
}
}
