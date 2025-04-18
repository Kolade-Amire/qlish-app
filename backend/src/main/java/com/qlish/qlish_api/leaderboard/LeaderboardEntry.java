package com.qlish.qlish_api.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeaderboardEntry {
    private String profileName;
    private Long points;
}
