package com.example.tsipadan.petproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {

    private UUID id;
    private LocalDateTime created;
    private String howPay;
    private String howDeliver;
    private String statusPay;
    private String statusOrder;
    private boolean approvedFlag;
    private List<ItemDTO> items;
    private UserDTO userDTO;

}
