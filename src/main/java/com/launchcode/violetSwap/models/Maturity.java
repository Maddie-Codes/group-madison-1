package com.launchcode.violetSwap.models;

public enum Maturity {
    SEEDS ("Seeds"),
    CUTTING("Cutting"),
    YOUNG_PLANT("Young Plant"),
    MATURE_PLANT("Mature Plant")
    ;

    private final String type;

    private Maturity(String type){
        this.type = type;
    }

    public String getDisplayName() {
        return type;
    }
}
