package com.noelpinto47.planora.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.noelpinto47.planora.dto.UserDTO;
import com.noelpinto47.planora.entities.User;
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

    public User authenticateUser(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public boolean findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public User saveUser(User newUser) {
        return userRepository.save(newUser);
    }
}
