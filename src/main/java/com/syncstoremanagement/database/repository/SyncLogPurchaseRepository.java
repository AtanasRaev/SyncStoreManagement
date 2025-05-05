package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.SyncLogPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncLogPurchaseRepository extends JpaRepository<SyncLogPurchase, Long> {
}
