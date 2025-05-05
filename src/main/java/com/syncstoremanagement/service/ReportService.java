package com.syncstoremanagement.service;

import com.syncstoremanagement.database.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    List<Long> sync(List<ReportDTO> listDTO);
}
