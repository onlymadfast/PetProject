package com.example.tsipadan.petproject.dto;

import com.example.tsipadan.petproject.model.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @JsonIgnore
    private String role;

//    @JsonInclude(Include.NON_NULL)
    private AddressDTO address;
    private List<OrderDTO> order;

}
