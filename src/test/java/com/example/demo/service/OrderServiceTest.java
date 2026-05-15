package com.example.demo.service;

import com.example.demo.dto.OrderCreateDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.UserPoints;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PointsRecordRepository;
import com.example.demo.repository.UserPointsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserPointsRepository userPointsRepository;

    @Mock
    private PointsRecordRepository pointsRecordRepository;

    @InjectMocks
    private OrderService orderService;

    private UserPoints userPoints;

    @BeforeEach
    void setUp() {
        userPoints = new UserPoints();
        userPoints.setUserId(1L);
        userPoints.setBalance(0);
    }

    @Test
    void testCreateOrder_100Yuan_ShouldGet10Points() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setUserId(1L);
        dto.setAmount(BigDecimal.valueOf(100));

        when(userPointsRepository.findByUserId(1L)).thenReturn(userPoints);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });
        when(pointsRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(dto);

        assertEquals(10, order.getPointsEarned());
        assertEquals(10, userPoints.getBalance());
    }

    @Test
    void testCreateOrder_150Yuan_ShouldGet10Points() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setUserId(1L);
        dto.setAmount(BigDecimal.valueOf(150));

        when(userPointsRepository.findByUserId(1L)).thenReturn(userPoints);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });
        when(pointsRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(dto);

        assertEquals(10, order.getPointsEarned());
    }

    @Test
    void testCreateOrder_250Yuan_ShouldGet20Points() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setUserId(1L);
        dto.setAmount(BigDecimal.valueOf(250));

        when(userPointsRepository.findByUserId(1L)).thenReturn(userPoints);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });
        when(pointsRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(dto);

        assertEquals(20, order.getPointsEarned());
    }

    @Test
    void testCreateOrder_99Yuan_ShouldGet0Points() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setUserId(1L);
        dto.setAmount(BigDecimal.valueOf(99));

        when(userPointsRepository.findByUserId(1L)).thenReturn(userPoints);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });
        when(pointsRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(dto);

        assertEquals(0, order.getPointsEarned());
    }

    @Test
    void testCreateOrder_UserNotExist_ShouldThrowException() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setUserId(999L);
        dto.setAmount(BigDecimal.valueOf(100));

        when(userPointsRepository.findByUserId(999L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(dto));
    }
}