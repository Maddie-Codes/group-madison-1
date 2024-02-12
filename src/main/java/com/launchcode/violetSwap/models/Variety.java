package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Variety extends AbstractEntity{

    private String name;

    private String searchTerm;

    @OneToMany(mappedBy = "variety")
    private Set<Listing> listings = new HashSet<>();


    public Variety() {
        // default constructor
    }

    public Variety(String name){
        this.name = name;
        this.searchTerm = Search.removeExtraChars(name); //method removes extra chars and uppercases it to make it case-insensitive
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Listing> getListings() {
        return listings;
    }

    public void setListings(Set<Listing> listings) {
        this.listings = listings;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}