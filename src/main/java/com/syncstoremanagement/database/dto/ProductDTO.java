package com.syncstoremanagement.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private String name;

    private BigDecimal quantity;

    private BigDecimal price;

    private String unit;

    private String type;

    private String image;

    private LocalDateTime lastUpdated;

    private boolean isSelected;
}
