package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByAppIdIn(List<Long> appIds);
}
