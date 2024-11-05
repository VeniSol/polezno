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
    @Column(name = "user_name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Order> orders;

    public User(String login, String name, String password, Role role) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {}

}