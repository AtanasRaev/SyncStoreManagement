package com.syncstoremanagement.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseDTO {
    private Long id;

    private ProductDTO product;

    private BigDecimal quantity;

    private BigDecimal  price;

    private LocalDateTime createdAt;
}
