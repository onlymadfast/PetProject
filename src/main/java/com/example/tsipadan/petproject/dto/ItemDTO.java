package com.example.tsipadan.petproject.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDTO {

    private UUID id;
    private LocalDateTime created;
    private String name;
    private String image;
    private String description;
    private Integer quantity;
    private Integer quantityOnWarehouse;
    private BigDecimal price;
    private String category;
    private Integer countBuy;
    private Boolean onSale;
    private OrderDTO order;

}
