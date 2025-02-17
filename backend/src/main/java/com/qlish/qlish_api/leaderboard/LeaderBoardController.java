package com.qlish.qlish_api.leaderboard;

import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderBoardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/all-time")
    public ResponseEntity<List<LeaderboardEntry>> getAllTimeLeaderboard(){
        var leaderboard = leaderboardService.getAllTimeLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<LeaderboardEntry>> getDailyLeaderboard(){
        var leaderboard = leaderboardService.getDailyLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
