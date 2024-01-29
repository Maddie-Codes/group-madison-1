package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Varieties{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "varieties")
    private Set<Listing> listings = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Varieties() {
        // default constructor
    }

    public Set<Listing> getListings() {
        return listings;
    }

    public void setListings(Set<Listing> listings) {
        this.listings = listings;
    }
}