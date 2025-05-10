package com.noelpinto47.planora.validations;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class RegisterSchema {

    @Data
    public static class RegisterRequestSchema {
        @NotEmpty(message = "Name is required")
        @JsonProperty("name")
        private String name;

        @NotEmpty(message = "Email is required")
        @JsonProperty("email")
        private String email;

        @NotEmpty(message = "Password is required")
        @JsonProperty("password")
        private String password;

        @NotEmpty(message = "Login type is required")
        @JsonProperty("login_type")
        @Pattern(regexp = "EMAIL_PASSWORD|GOOGLE|FACEBOOK|APPLE", message = "Login type must be one of EMAIL_PASSWORD, GOOGLE, FACEBOOK, APPLE")
        private String loginType;
    }
}
