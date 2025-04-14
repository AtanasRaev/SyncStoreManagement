package com.syncstoremanagement.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_synced", nullable = false)
    private boolean isSynced;

    public Report(Product product, BigDecimal quantity, LocalDateTime createdAt, boolean isSynced) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.isSynced = isSynced;
    }

    public Report() {
    }
}
