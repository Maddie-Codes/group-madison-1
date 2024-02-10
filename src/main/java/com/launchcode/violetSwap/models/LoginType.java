package com.launchcode.violetSwap.models;

public enum LoginType {
    FORM ("FORM"),
    OAUTH_GITHUB ("OAUTH_GITHUB"),
    OAUTH_GOOGLE ("OAUTH_GOOGLE");

    private final String type;

    private LoginType(String type) {this.type = type;}

    public String getType() {return type;}
}
