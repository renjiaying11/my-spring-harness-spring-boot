package com.example.demo.service;

import com.example.demo.dto.PointsRecordDTO;
import com.example.demo.dto.UserPointsDTO;
import com.example.demo.entity.PointsRecord;
import com.example.demo.entity.User;
import com.example.demo.entity.UserPoints;
import com.example.demo.repository.PointsRecordRepository;
import com.example.demo.repository.UserPointsRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointsService {

    private final UserRepository userRepository;
    private final UserPointsRepository userPointsRepository;
    private final PointsRecordRepository pointsRecordRepository;

    public UserPointsDTO getUserPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        UserPoints userPoints = userPointsRepository.findByUserId(userId);
        if (userPoints == null) {
            throw new RuntimeException("用户积分数据不存在");
        }

        List<PointsRecord> records = pointsRecordRepository.findByUserId(userId);
        List<PointsRecordDTO> recordDTOs = records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        UserPointsDTO dto = new UserPointsDTO();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setBalance(userPoints.getBalance());
        dto.setRecentRecords(recordDTOs);
        return dto;
    }

    public List<PointsRecordDTO> getPointsRecords(Long userId) {
        List<PointsRecord> records = pointsRecordRepository.findByUserId(userId);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PointsRecordDTO convertToDTO(PointsRecord record) {
        PointsRecordDTO dto = new PointsRecordDTO();
        dto.setId(record.getId());
        dto.setPoints(record.getPoints());
        dto.setType(record.getType());
        dto.setOrderId(record.getOrderId());
        dto.setCreatedAt(record.getCreatedAt());
        return dto;
    }
}