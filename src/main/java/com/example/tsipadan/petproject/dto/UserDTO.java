package com.example.tsipadan.petproject.dto;

import com.example.tsipadan.petproject.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private LocalDateTime created;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthday;
    private Set<Role> roles;

    private AddressDTO address;
    private List<OrderDTO> order;

}
