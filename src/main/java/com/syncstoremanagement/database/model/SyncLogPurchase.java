package com.syncstoremanagement.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sync_log_purchase")
public class SyncLogPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "new_created_rows")
    private int newCreatedRows;

    @Column(name = "updated_rows")
    private int updatedRows;

    public SyncLogPurchase(int newCreatedRows, int updatedRows) {
        this.newCreatedRows = newCreatedRows;
        this.updatedRows = updatedRows;
    }

    public SyncLogPurchase() {
    }
}
