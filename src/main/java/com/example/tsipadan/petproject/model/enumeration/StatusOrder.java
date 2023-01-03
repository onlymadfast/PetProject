package com.example.tsipadan.petproject.model.enumeration;

public enum StatusOrder {

    PENDING_PAYMENT("PENDING_PAYMENT"),
    WITHOUT_ADDRESS("WITHOUT_ADDRESS"),
    ON_THE_WAY("ON_THE_WAY"),
    DELIVERED("DELIVERED");


    private final String statusOrder;

    StatusOrder(String status) {
        this.statusOrder = status;
    }

    public String getStatus() {
        return this.statusOrder;
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
