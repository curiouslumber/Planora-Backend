package com.noelpinto47.planora.validations;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class LoginSchema {

    @Data
    public static class LoginRequestSchema {

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @JsonProperty("email")
        private String email;

        @NotBlank(message = "Password is required")
        @JsonProperty("password")
        private String password;
    }

    @Data
    public static class LoginResponseSchema {
        @JsonProperty("name")
        private String name;
        @JsonProperty("email")
        private String email;
        @JsonProperty("login_type")
        private String loginType;
        @JsonProperty("profile_picture")
        private String profilePicture;
    }
}
