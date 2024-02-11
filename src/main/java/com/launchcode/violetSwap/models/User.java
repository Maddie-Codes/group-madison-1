package com.launchcode.violetSwap.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    @NotBlank
    @Size(max=100)
    private String username;

    @NotNull
    @NotBlank
    private String pwHash;

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

    @OneToMany (mappedBy = "user")
    private final List<Listing> Listings = new ArrayList<>();


    public User() {}

    public User(String username, String password, String email, String city, String state) {
        super();
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.email = email;
        this.city = city;
        this.state = state;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNewPassword(String password) {
        this.pwHash = encoder.encode(password);
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

//    public List<Listing> getUserListings() {
//        return userListings;
//    }
}
