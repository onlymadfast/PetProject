package com.example.tsipadan.petproject.model;

import com.example.tsipadan.petproject.model.enumeration.HowDeliver;
import com.example.tsipadan.petproject.model.enumeration.HowPay;
import com.example.tsipadan.petproject.model.enumeration.StatusOrder;
import com.example.tsipadan.petproject.model.enumeration.StatusPay;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "order_date")
    private LocalDateTime orderDate =  LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "how_pay", nullable = false)
    private HowPay howPay;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "how_deliver", nullable = false)
    private HowDeliver howDeliver;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_pay", nullable = false)
    private StatusPay statusPay;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_order", nullable = false)
    private StatusOrder statusOrder;

    @Column(name = "approvedFlag")
    private boolean approvedFlag;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Item> items;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
