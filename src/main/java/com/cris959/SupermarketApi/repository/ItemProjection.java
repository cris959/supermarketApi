package com.cris959.SupermarketApi.repository;

public interface ItemProjection {
    Long getId();
    String getProductName();
    Integer getQuantity();
    Double getUnitPrice();
    Double getSubTotal();
}
