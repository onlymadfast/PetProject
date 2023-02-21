package com.example.tsipadan.petproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "our_user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Column(unique = true, name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; //хранить пароль в byte[] ??

    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "roles")
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> order;

}
