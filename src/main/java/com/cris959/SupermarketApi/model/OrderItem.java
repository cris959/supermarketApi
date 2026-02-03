package com.cris959.SupermarketApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "order_items") // Nombre explícito en plural
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Venta
    @ManyToOne(fetch = FetchType.LAZY) // Lazy por rendimiento
    @JoinColumn(name = "sale_id", nullable = false) // Nombre de la FK claro
    private Sale sale;

    // Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // Nombre de la FK claro
    private Product product;
    private Integer quantity;
    private Double unitPrice;
}
