package com.noelpinto47.planora.controllers;

import org.springframework.web.bind.annotation.*;

import com.noelpinto47.planora.entities.User;
import com.noelpinto47.planora.services.UserService;
import com.noelpinto47.planora.utils.CustomResponseEntity;
import com.noelpinto47.planora.validations.LoginSchema;
import com.noelpinto47.planora.validations.LoginSchema.LoginRequestSchema;
import com.noelpinto47.planora.validations.RegisterSchema.RegisterRequestSchema;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final Validator validator;

    public UserController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    // Login user via email and password
    @PostMapping("/login")
    public ResponseEntity<CustomResponseEntity> loginUser(@RequestBody LoginRequestSchema reqBody) {
        if (reqBody == null) {
            return ResponseEntity.badRequest()
                    .body(new CustomResponseEntity(false, "All fields are required", null));
        }

        // Check the validation with the schema
        Set<ConstraintViolation<LoginRequestSchema>> violations = validator
                .validate(reqBody);
        if (!violations.isEmpty()) {
            // Construct error message from violations
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce("", (acc, msg) -> acc.isEmpty() ? msg : acc + "; " + msg);
            return ResponseEntity.status(400).body(new CustomResponseEntity(false, errorMessage, null));
        }

        try {
            User user = userService.authenticateUser(reqBody.getEmail(),
                    reqBody.getPassword());
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
    public ResponseEntity<CustomResponseEntity> registerUser(@RequestBody RegisterRequestSchema reqBody) {
        if (reqBody == null) {
            return ResponseEntity.badRequest()
                    .body(new CustomResponseEntity(false, "All fields are required", null));
        }

        // Check the validation with the schema
        Set<ConstraintViolation<RegisterRequestSchema>> violations = validator
                .validate(reqBody);
        if (!violations.isEmpty()) {
            // Construct error message from violations
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce("", (acc, msg) -> acc.isEmpty() ? msg : acc + "; " + msg);
            return ResponseEntity.status(400).body(new CustomResponseEntity(false, errorMessage, null));
        }

        try {
            boolean existingUser = userService.findByEmail(reqBody.getEmail());
            if (existingUser) {
                return ResponseEntity.ok(new CustomResponseEntity(false, "User already exists", null));
            }

            User newUser = new User();
            newUser.setName(reqBody.getName());
            newUser.setEmail(reqBody.getEmail());
            newUser.setPassword(reqBody.getPassword());
            newUser.setLoginType(reqBody.getLoginType());
            newUser.setIsActive(true);
            User savedUser = userService.saveUser(newUser);
            if (savedUser == null) {
                return ResponseEntity.ok(new CustomResponseEntity(false, "User registration failed", null));
            }
            newUser.setPassword(null);

            return ResponseEntity.ok(new CustomResponseEntity(true, "User registered successfully", newUser));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CustomResponseEntity(false, e.getMessage(), null));
        }
    }
}
