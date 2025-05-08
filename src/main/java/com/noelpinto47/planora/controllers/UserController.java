package com.noelpinto47.planora.controllers;

import org.springframework.web.bind.annotation.*;

import com.noelpinto47.planora.dto.UserDTO;
import com.noelpinto47.planora.services.UserService;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
