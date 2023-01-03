package com.example.tsipadan.petproject.model.enumeration;

public enum Role {

    USER("USER"),
    ADMIN("ADMIN"),
    DEVELOPER("DEVELOPER"),
    ANONYMOUS("ANONYMOUS");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return getRole();
    }
}
