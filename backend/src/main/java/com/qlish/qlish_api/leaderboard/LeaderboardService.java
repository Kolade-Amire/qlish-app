package com.qlish.qlish_api.leaderboard;

import com.qlish.qlish_api.exceptions.LeaderboardException;
import com.qlish.qlish_api.user.services.UserService;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.qlish.qlish_api.util.AppConstants.ALL_TIME_LEADERBOARD_KEY;

@RequiredArgsConstructor
@Service
public class LeaderboardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardService.class);


    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;


    @Transactional
    public void updateDailyLeaderboard(LeaderboardEntry entry){
        String key = getDailyLeaderboardKey();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            setDailyLeaderboardKeyExpiration(key);
        }
        updateLeaderboard(entry, key);
    }

    @Transactional
    public void updateAllTimeLeaderboard(LeaderboardEntry entry){
        updateLeaderboard(entry, ALL_TIME_LEADERBOARD_KEY);
    }

    @Transactional(readOnly = true)
    public List<LeaderboardEntry> getDailyLeaderboard(){
        String key = getDailyLeaderboardKey();
        return getLeaderBoard(key);
    }

    @Transactional(readOnly = true)
    public List<LeaderboardEntry> getAllTimeLeaderboard(){
        return getLeaderBoard(ALL_TIME_LEADERBOARD_KEY);
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public void updateLeaderboard(LeaderboardEntry entry, String key){

        Double minScore = getMinimumScore(key);

        if (minScore == null || entry.getPoints() > minScore) {

            // Check if user exists in leaderboard
            Double existingScore = redisTemplate.opsForZSet().score(key, entry.getProfileName());

            if (existingScore != null) {
                // If user already exists, increment their score
                redisTemplate.opsForZSet().incrementScore(key, entry.getProfileName(), entry.getPoints());
            } else {
                // If user doesn't exist, add to leaderboard
                redisTemplate.opsForZSet().add(key, entry.getProfileName(), entry.getPoints());

                // Remove the lowest-ranked user if the leaderboard exceeds 20 entries
                trimLeaderboard(key);
            }
        }
    }

    //Gets top 20 users eligible for leaderboard entry
    @Transactional(readOnly = true)
    public List<LeaderboardEntry> getLeaderBoard(String key) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> topEntries = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 19);

            if (topEntries == null) {
                return Collections.emptyList();
            }
            //map results to leaderboard entry objects
            return topEntries.stream()
                    .filter(entry -> entry.getValue() != null && entry.getScore() != null)
                    .map(entry ->
                            LeaderboardEntry.builder()
                                    .points(entry.getScore().longValue())
                                    .profileName(entry.getValue().toString())
                                    .build()
                    ).toList();

        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while trying to retrieve leaderboard", e);
            throw new LeaderboardException("Unable to retrieve leaderboard");
        }
    }


    /*
     * Periodic task to synchronize Redis leaderboard with MongoDB.
     * Ensures consistency and handles edge cases like missed updates.
     */
    @Scheduled(cron = "0 0 0,12 * * ?", zone = "UTC") // Runs twice daily, at 12 morning and noon
    @Transactional
    @Async("asyncTaskExecutor")
    public void synchronizeAllTimeLeaderboardWithDatabase() {
        try {
            LOGGER.info("Synchronizing Redis leaderboard with MongoDB...");

            var topUsersFromDB = userService.getUsersWithTop20Points();

            // Clear leaderboard
            redisTemplate.delete(ALL_TIME_LEADERBOARD_KEY);

            // Repopulate leaderboard with top users from MongoDB
            for (var user : topUsersFromDB) {
                redisTemplate.opsForZSet().add(ALL_TIME_LEADERBOARD_KEY, user.getProfileName(), user.getAllTimePoints());
            }

            LOGGER.info("Redis leaderboard synchronized successfully.");
        } catch (Exception e) {
            LOGGER.error("An error occurred while synchronizing leaderboard. \nTimestamp: {}\n", LocalDateTime.now(ZoneOffset.UTC), e);
        }
    }

    private Double getMinimumScore(String key) {
        Set<ZSetOperations.TypedTuple<Object>> entries = redisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
        if (entries != null && !entries.isEmpty()) {
            return entries.iterator().next().getScore();
        }
        return null;
    }

    //Trims the leaderboard to retain only the top 20 users
    private void trimLeaderboard(String key) {
        Long leaderboardSize = redisTemplate.opsForZSet().size(key);
        if (leaderboardSize != null && leaderboardSize > 20) {
            redisTemplate.opsForZSet().popMin(key);
        }
    }

    private String getDailyLeaderboardKey() {
        String date = LocalDate.now(ZoneOffset.UTC).toString();
        return AppConstants.DAILY_LEADERBOARD_KEY_PREFIX + date;
    }

    public void setDailyLeaderboardKeyExpiration(String key) {
        // Calculate time to midnight
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1);
        long secondsToMidnight = Duration.between(now, midnight).getSeconds();
        redisTemplate.expire(key, secondsToMidnight, TimeUnit.SECONDS);
    }
}


