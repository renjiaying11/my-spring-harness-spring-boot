package com.example.demo.repository;

import com.example.demo.entity.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {

    UserPoints findByUserId(Long userId);
}