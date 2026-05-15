package com.example.demo.service;

import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserPoints;
import com.example.demo.repository.UserPointsRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPointsRepository userPointsRepository;

    @Transactional
    public User register(UserRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        userRepository.save(user);

        UserPoints userPoints = new UserPoints();
        userPoints.setUserId(user.getId());
        userPoints.setBalance(0);
        userPointsRepository.save(userPoints);

        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}