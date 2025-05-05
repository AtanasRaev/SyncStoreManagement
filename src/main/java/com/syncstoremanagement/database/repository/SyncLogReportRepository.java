package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.SyncLogReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncLogReportRepository extends JpaRepository<SyncLogReport, Long> {
}
