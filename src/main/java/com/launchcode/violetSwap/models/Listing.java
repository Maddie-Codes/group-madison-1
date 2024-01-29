package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Listing extends AbstractEntity {
    @Size(max = 100)
    private String variety;

    public Set<Varieties> getVarieties() {
        return varieties;
    }

    public void setVarieties(Set<Varieties> varieties) {
        this.varieties = varieties;
    }

    @ManyToMany
    private Set<Varieties> varieties = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Maturity maturity; //enum Maturity
    @Size(max = 300)
    private String description;

    //constructors
    public Listing() {

    }

    public Listing(String variety, Maturity maturity, String description){ //Initialize id and fields.
        super(); //for id
        this.variety = variety;
        this.maturity = maturity;
        this.description = description;
    }



    //getters and setters
    public String getVariety() {
        return variety;
    }
    public void setVariety(String variety) {
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
