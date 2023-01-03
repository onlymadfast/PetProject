package com.example.tsipadan.petproject.model.enumeration;

public enum Category {

    CLOTHING("CLOTHING"),
    SHOES("SHOES"),
    ACCESSORIES("ACCESSORIES");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return getCategory();
    }
}
