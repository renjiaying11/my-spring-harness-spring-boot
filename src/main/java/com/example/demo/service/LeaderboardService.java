package com.example.demo.service;

import com.example.demo.dto.LeaderboardDTO;
import com.example.demo.dto.UserRankDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserPoints;
import com.example.demo.repository.UserPointsRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final UserPointsRepository userPointsRepository;
    private final UserRepository userRepository;

    private List<LeaderboardDTO> cachedLeaderboard;
    private long lastUpdateTime = 0;
    private static final long CACHE_DURATION = 5 * 60 * 1000;

    public List<LeaderboardDTO> getTop100() {
        long now = System.currentTimeMillis();
        if (cachedLeaderboard == null || now - lastUpdateTime > CACHE_DURATION) {
            cachedLeaderboard = refreshLeaderboard();
            lastUpdateTime = now;
        }
        return cachedLeaderboard;
    }

    private List<LeaderboardDTO> refreshLeaderboard() {
        List<UserPoints> topPoints = userPointsRepository.findTop100ByOrderByBalanceDesc();

        List<Long> userIds = topPoints.stream()
                .map(UserPoints::getUserId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAllById(userIds);
        Map<Long, String> usernameMap = users.stream()
                .collect(Collectors.toMap(User::getId, User:: getUsername));

        List<LeaderboardDTO> result = new ArrayList<>();
        int rank = 1;
        for (UserPoints up : topPoints) {
            LeaderboardDTO dto = new LeaderboardDTO();
            dto.setUserId(up.getUserId());
            dto.setUsername(usernameMap.getOrDefault(up.getUserId(), "未知用户"));
            dto.setBalance(up.getBalance());
            dto.setRank(rank++);
            result.add(dto);
        }
        return result;
    }

    public UserRankDTO getUserRank(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        UserPoints userPoints = userPointsRepository.findByUserId(userId);
        if (userPoints == null) {
            throw new RuntimeException("用户积分数据不存在");
        }

        Integer rank = userPointsRepository.findUserRankByUserId(userId);

        UserRankDTO dto = new UserRankDTO();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setBalance(userPoints.getBalance());
        dto.setRank(rank);
        dto.setTop100(getTop100());
        dto.setLastUpdateTime(lastUpdateTime);
        return dto;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}