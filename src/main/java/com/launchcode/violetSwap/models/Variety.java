package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Variety extends AbstractEntity{

    private String name;

    private String imgPath;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // Public method to access imagePath indirectly
    public String getImagePathForThymeleaf() {
        return getImgPath();
    }

    @OneToMany(mappedBy = "variety", cascade = CascadeType.ALL)
    private Set<Listing> listings = new HashSet<>();

    public Variety() {
        // default constructor
    }

    @Override
    public String toString() {
        return name; // Assuming `name` is a unique identifier for the variety
    }

    public Set<Listing> getListings() {
        return listings;
    }

    public void setListings(Set<Listing> listings) {
        this.listings = listings;
    }
}