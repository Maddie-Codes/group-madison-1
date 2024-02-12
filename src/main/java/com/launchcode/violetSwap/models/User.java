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
    private String loginType;

    private String pwHash;

    private String email;

    private Integer zipcode;

    @OneToMany (mappedBy = "user")
    private final List<Listing> Listings = new ArrayList<>();


    public User() {}

    public User(String username, LoginType loginType) {
        super();
        this.username = username;
        this.loginType = loginType.getType();
    }

    public User(String username, LoginType loginType, String password, String email, String zipcode) {
        super();
        this.username = username;
        this.loginType = loginType.getType();
        this.pwHash = encoder.encode(password);
        this.email = email;
        this.zipcode = Integer.valueOf(zipcode);
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

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = Integer.valueOf(zipcode);
    }

    public List<Listing> getListings(){
        return Listings;
    }

//    public List<Listing> getUserListings() {
//        return userListings;
//    }
}
