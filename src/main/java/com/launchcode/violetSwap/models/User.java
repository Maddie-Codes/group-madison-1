package com.launchcode.violetSwap.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotNull
    @NotBlank
    @Size(max=100)
    private String username;

    @NotNull
    @NotBlank
    @Size(min=10, max=100)
    private String password;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(max=100)
    private String city;

    //Setting requirements for now, intending to lock this down to an enum or must come from google maps integration later on
    @NotNull
    @NotBlank
    @Size(max=30)
    private String state;


    private List<Listing> userListings = new ArrayList<>();

    public User() {}

    public User(String username, String password, String email, String city, String state) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.city = city;
        this.state = state;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Listing> getUserListings() {
        return userListings;
    }
}
