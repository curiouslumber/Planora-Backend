package com.noelpinto47.planora.controllers;

import org.springframework.web.bind.annotation.*;

import com.noelpinto47.planora.services.UserService;
import com.noelpinto47.planora.utils.CustomResponseEntity;
import com.noelpinto47.planora.validations.LoginSchema.LoginRequestSchema;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Login user via email and password
    @PostMapping("/login")
    public ResponseEntity<CustomResponseEntity> loginUser(@RequestBody LoginRequestSchema loginRequest) {
        if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(new CustomResponseEntity(false, "Email and Password are required", null));
        }

        try {
            boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(),
                    loginRequest.getPassword());

            if (isAuthenticated) {
                return ResponseEntity.ok(new CustomResponseEntity(true, "Login successful",
                        null));
            } else {
                return ResponseEntity.ok(new CustomResponseEntity(false,
                        "Invalid credentials", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CustomResponseEntity(false, e.getMessage(), null));
        }
    }
}
