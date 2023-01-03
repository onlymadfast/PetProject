package com.example.tsipadan.petproject.model;

import com.example.tsipadan.petproject.model.enumeration.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "image")
    private String image; //будет хранится ссылка на сторонний ресурс

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantityOnWarehouse")
    private Integer quantityOnWarehouse;

    @Column(name = "quantity")
    private Integer quantity; //quantity in clients card

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category",nullable = false)
    private Category category;

    @Column(name = "countBuy")
    private int countBuy;

    @Column(name = "on_sale")
    private boolean onSale; //товары на скидку можно помечать,
    // например если они созданы давно,
    // но количество покупок товара маленькое

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_table_id")
    private Order order;

}
