package com.launchcode.violetSwap.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(min=5, max=100, message="Username must be between 5 and 100 characters")
    private String username;

    @NotNull
    @NotBlank
    @Size(min=10, max=150, message="Username must be between 10 and 150 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
