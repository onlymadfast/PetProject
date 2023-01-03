package com.example.tsipadan.petproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AddressDTO {

    private UUID id;
    private LocalDateTime created;
    private String country;
    private String city;
    private String zip;
    private String street;
    private String house;
    private String apartment;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String fullAddress;

    public String getFullAddress() {
        return zip + ", " + country
                + " " + city + ", " + street + " "
                + house + "/" + apartment;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = zip + ", " + country
                + " " + city + ", " + street + " "
                + house + "/" + apartment;
    }
}
