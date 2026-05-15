package com.example.demo.controller;

import com.example.demo.dto.PointsRecordDTO;
import com.example.demo.dto.UserPointsDTO;
import com.example.demo.service.PointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointsController {

    private final PointsService pointsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPointsDTO> getUserPoints(@PathVariable Long userId) {
        UserPointsDTO dto = pointsService.getUserPoints(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/records/{userId}")
    public ResponseEntity<List<PointsRecordDTO>> getPointsRecords(@PathVariable Long userId) {
        List<PointsRecordDTO> records = pointsService.getPointsRecords(userId);
        return ResponseEntity.ok(records);
    }
}