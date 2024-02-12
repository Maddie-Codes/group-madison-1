package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
public class Listing extends AbstractEntity {

    @ManyToOne
    private Variety variety;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Maturity maturity; //enum Maturity
    @Size(max = 300)
    private String description;

    //constructors
    public Listing() {

    }

    public Listing(Variety variety, Maturity maturity, String description){ //Initialize id and fields.
        super(); //for id
        this.variety = variety;
        this.maturity = maturity;
        this.description = description;
    }



    //getters and setters


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public Maturity getMaturity() {
        return maturity;
    }
    public void setMaturity(Maturity maturity) {
        this.maturity = maturity;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
