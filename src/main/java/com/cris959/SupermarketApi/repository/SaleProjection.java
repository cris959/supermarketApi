package com.cris959.SupermarketApi.repository;

import java.time.LocalDate;

public interface SaleProjection {

    Long getId();
    LocalDate getDate();
    String getStatus();
    Double getTotal();
    Long getBranchId();
    String getBranchName();
}
