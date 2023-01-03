package com.example.tsipadan.petproject.model.enumeration;

public enum StatusPay {

    NOT_PAID("NOT_PAID"),
    PAID("PAID");

    private final String statusPay;

    StatusPay(String statusPay) {
        this.statusPay = statusPay;
    }

    public String getStatusPay() {
        return this.statusPay;
    }

    @Override
    public String toString() {
        return getStatusPay();
    }
}
