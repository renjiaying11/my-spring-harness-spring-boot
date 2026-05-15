package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserPointsDTO {

    private Long userId;
    private String username;
    private Integer balance;
    private List<PointsRecordDTO> recentRecords;
}