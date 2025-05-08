package com.noelpinto47.planora.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.noelpinto47.planora.dto.UserDTO;
import com.noelpinto47.planora.mappers.UserMapper;
import com.noelpinto47.planora.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.toUserDTOs(userRepository.findAll());
    }
}
