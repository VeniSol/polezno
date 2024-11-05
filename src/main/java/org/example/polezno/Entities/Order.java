package org.example.polezno.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private String status;
    @Column(name = "address")
    private String address;
    @Column(name = "deliverer")
    private String deliverer;

}
