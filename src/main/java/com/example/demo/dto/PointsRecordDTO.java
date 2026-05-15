package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PointsRecordDTO {

    private Long id;
    private Integer points;
    private String type;
    private Long orderId;
    private LocalDateTime createdAt;
}