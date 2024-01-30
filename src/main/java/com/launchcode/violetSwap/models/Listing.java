package com.launchcode.violetSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Listing extends AbstractEntity {
//    @Size(max = 100)
//    private String variety;

//    public Set<Varieties> getVarieties() {
//        return varieties;
//    }
//
//    public void setVarieties(Set<Varieties> varieties) {
//        this.varieties = varieties;
//    }

//    @ManyToMany
//    private Set<Varieties> varieties = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "variety_id")
    @NotNull
    private Variety variety;

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
