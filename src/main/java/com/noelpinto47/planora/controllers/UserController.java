package com.noelpinto47.planora.controllers;

import org.springframework.web.bind.annotation.*;

import com.noelpinto47.planora.entities.User;
import com.noelpinto47.planora.services.UserService;
import com.noelpinto47.planora.utils.CustomResponseEntity;
import com.noelpinto47.planora.validations.LoginSchema;
import com.noelpinto47.planora.validations.LoginSchema.LoginRequestSchema;
import com.noelpinto47.planora.validations.RegisterSchema.RegisterRequestSchema;

import java.util.HashMap;
import java.util.Map;

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
            User user = userService.authenticateUser(loginRequest.getEmail(),
                    loginRequest.getPassword());
            if (user == null) {
                return ResponseEntity.ok(new CustomResponseEntity(false, "Invalid credentials", null));
            }

            LoginSchema.LoginResponseSchema responseObj = new LoginSchema.LoginResponseSchema();
            responseObj.setName(user.getName());
            responseObj.setEmail(user.getEmail());
            responseObj.setLoginType(user.getLoginType());
            responseObj.setProfilePicture(user.getProfilePicture());

            return ResponseEntity.ok(new CustomResponseEntity(true, "Login successful",
                    responseObj));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CustomResponseEntity(false, e.getMessage(), null));
        }
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<CustomResponseEntity> registerUser(@RequestBody RegisterRequestSchema user) {
        try {
            if (user == null || user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
                return ResponseEntity.badRequest()
                        .body(new CustomResponseEntity(false, "Email and Password are required", null));
            }

            boolean existingUser = userService.findByEmail(user.getEmail());
            if (existingUser) {
                return ResponseEntity.ok(new CustomResponseEntity(false, "User already exists", null));
            }

            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setLoginType(user.getLoginType());
            newUser.setIsActive(true);
            User savedUser = userService.saveUser(newUser);
            if (savedUser == null) {
                return ResponseEntity.ok(new CustomResponseEntity(false, "User registration failed", null));
            }

            return ResponseEntity.ok(new CustomResponseEntity(true, "User registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CustomResponseEntity(false, e.getMessage(), null));
        }
    }
}
