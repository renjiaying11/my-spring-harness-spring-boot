package com.example.demo.repository;

import com.example.demo.entity.PointsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRecordRepository extends JpaRepository<PointsRecord, Long> {

    List<PointsRecord> findByUserId(Long userId);
}