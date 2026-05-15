package com.example.demo.service;

import com.example.demo.dto.OrderCreateDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.PointsRecord;
import com.example.demo.entity.UserPoints;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PointsRecordRepository;
import com.example.demo.repository.UserPointsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserPointsRepository userPointsRepository;
    private final PointsRecordRepository pointsRecordRepository;

    private static final int POINTS_PER_100_YUAN = 10;

    @Transactional
    public Order createOrder(OrderCreateDTO dto) {
        UserPoints userPoints = userPointsRepository.findByUserId(dto.getUserId());
        if (userPoints == null) {
            throw new RuntimeException("用户不存在");
        }

        int points = calculatePoints(dto.getAmount());

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setAmount(dto.getAmount());
        order.setPointsEarned(points);
        orderRepository.save(order);

        userPoints.setBalance(userPoints.getBalance() + points);
        userPointsRepository.save(userPoints);

        PointsRecord record = new PointsRecord();
        record.setUserId(dto.getUserId());
        record.setPoints(points);
        record.setType("ORDER_REWARD");
        record.setOrderId(order.getId());
        pointsRecordRepository.save(record);

        return order;
    }

    private int calculatePoints(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100))
                .intValue() * POINTS_PER_100_YUAN;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }
}