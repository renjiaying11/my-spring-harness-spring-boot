package com.example.demo.controller;

import com.example.demo.dto.LeaderboardDTO;
import com.example.demo.dto.UserRankDTO;
import com.example.demo.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top100")
    public ResponseEntity<List<LeaderboardDTO>> getTop100() {
        return ResponseEntity.ok(leaderboardService.getTop100());
    }

    @GetMapping("/rank/{userId}")
    public ResponseEntity<UserRankDTO> getUserRank(@PathVariable Long userId) {
        return ResponseEntity.ok(leaderboardService.getUserRank(userId));
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshLeaderboard() {
        leaderboardService.getTop100();
        return ResponseEntity.ok("排行榜已刷新");
    }
}