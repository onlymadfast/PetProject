package com.example.tsipadan.petproject.model.enumeration;

public enum HowDeliver {

    DELIVERY("DELIVERY"),
    POINT_OF_DELIVERY("POINT_OF_DELIVERY");

    private final String howDeliver;

    HowDeliver(String howDeliver) {
        this.howDeliver = howDeliver;
    }

    public String getHowDeliver() {
        return this.howDeliver;
    }


    @Override
    public String toString() {
        return getHowDeliver();
    }
}
