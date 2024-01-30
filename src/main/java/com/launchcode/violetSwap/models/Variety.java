package com.launchcode.violetSwap.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Variety extends AbstractEntity{

    private String name;

//    @ManyToMany(mappedBy = "varieties")
//    private Set<Listing> listings = new HashSet<>();

    @OneToMany
    private final List<Listing> listings = new ArrayList<>();


    public Variety() {
        // default constructor
    }

    public Variety(String name){ //initialize name
        super(); //for id
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Listing> getListings() {
        return listings;
    }
//    public void setListings(Set<Listing> listings) {
//        this.listings = listings;
//    }
}