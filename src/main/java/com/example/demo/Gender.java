package com.example.demo;

public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE"),
    DIVERS("DIVERS");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}