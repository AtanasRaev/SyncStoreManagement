package com.syncstoremanagement.web;

import com.syncstoremanagement.database.dto.ReportDTO;
import com.syncstoremanagement.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncReportController {
    private final ReportService reportService;

    public SyncReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public ResponseEntity<Map<String, List<Long>>> syncReports(@RequestBody List<ReportDTO> listDTO) {
        List<Long> syncAppIds = this.reportService.sync(listDTO);
        return ResponseEntity.ok(Map.of(
                "appIds", syncAppIds
        ));
    }
}
