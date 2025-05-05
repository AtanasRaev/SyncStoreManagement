package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByAppIdIn(List<Long> appIds);
}
