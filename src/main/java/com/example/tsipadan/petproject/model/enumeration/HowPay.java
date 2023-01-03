package com.example.tsipadan.petproject.model.enumeration;

public enum HowPay {

    CARD("CARD"),
    CASH("CASH");

    private final String howPay;

    HowPay(String howPay) {
        this.howPay = howPay;
    }

    public String getHowPay() {
        return this.howPay;
    }


    @Override
    public String toString() {
        return getHowPay();
    }
}
