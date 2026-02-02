package com.cris959.SupermarketApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Venta
    @ManyToOne
    private Sale venta;

    // Producto
    @ManyToOne
    private Product producto;
    private Integer cantProd;
    private Double precio;
}
