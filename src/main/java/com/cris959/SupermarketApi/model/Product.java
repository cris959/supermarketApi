package com.cris959.SupermarketApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET active = false WHERE id = ?")
@SQLRestriction("active = true") // Reemplaza a @Where en versiones nuevas
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private boolean active = true; // Valor por defecto

    private String category;

    private Double price;

    private Integer stock;
}
