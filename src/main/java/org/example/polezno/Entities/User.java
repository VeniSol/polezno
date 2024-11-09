package org.example.polezno.Entities;

import lombok.*;

import jakarta.persistence.*;
import org.example.polezno.Entities.Order;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "number")
    private String number;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Order> orders;
    @OneToOne(mappedBy = "user",fetch = FetchType.EAGER)
    private Cart cart;
    public User(String login, String name, String password, Role role) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {}

}