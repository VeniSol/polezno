package org.example.polezno.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name_prod")
    private String nameProd;
    @Column(name = "price_prod")
    private Double priceProd;
    @Column(name = "quantity_prod")
    private int quantityProd;
    @Column(name = "image")
    private String image;

    public Product() {
    }

}
