package org.example.polezno.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    List<CartItem> cartItems;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "address")
    private String address;
    @Column(name = "deliverer")
    private String deliverer;
    @Column(name = "total_sum")
    private Double totalSum;
    @Column(name = "order_date_time")
    private LocalDateTime orderDateTime;
    public Order(User user, Status status,String address,Double totalSum) {
        this.user = user;
        this.status = status;
        this.address = address;
        this.totalSum = totalSum;
    }

}
