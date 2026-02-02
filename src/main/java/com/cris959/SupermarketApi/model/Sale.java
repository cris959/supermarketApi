package com.cris959.SupermarketApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String estado;

    private Double total;

    @ManyToOne
    private Branch sucursal;

    @OneToMany(mappedBy = "venta")
    private List<OrderItem> detalleVentas = new ArrayList<>();
}
