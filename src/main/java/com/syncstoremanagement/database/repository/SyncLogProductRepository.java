package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.SyncLogProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncLogProductRepository extends JpaRepository<SyncLogProduct, Long> {
}
