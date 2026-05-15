package com.example.demo.dto;

import lombok.Data;

@Data
public class LeaderboardDTO {

    private Long userId;
    private String username;
    private Integer balance;
    private Integer rank;
}