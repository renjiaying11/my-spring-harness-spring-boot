package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserRankDTO {

    private Long userId;
    private String username;
    private Integer balance;
    private Integer rank;
    private List<LeaderboardDTO> top100;
    private Long lastUpdateTime;
}