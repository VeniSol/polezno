package org.example.polezno.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total_price")
    private Double total_price;
    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;
    @ManyToOne()
    @JoinColumn(name = "id_product")
    private Product product;

    public CartItem(Integer quantity, Double total_price, Order order, Product product) {
        this.quantity = quantity;
        this.total_price = total_price;
        this.order = order;
        this.product = product;
    }

    public CartItem() {

    }

}
