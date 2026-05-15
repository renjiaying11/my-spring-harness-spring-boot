package com.example.demo.repository;

import com.example.demo.entity.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {

    UserPoints findByUserId(Long userId);

    @Query("SELECT up FROM UserPoints up ORDER BY up.balance DESC")
    List<UserPoints> findTop100ByOrderByBalanceDesc();

    @Query(value = "SELECT COUNT(*) + 1 FROM user_points WHERE balance > (SELECT balance FROM user_points WHERE user_id = :userId)", nativeQuery = true)
    Integer findUserRankByUserId(@Param("userId") Long userId);
}