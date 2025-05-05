package com.syncstoremanagement.service.impl;

import com.syncstoremanagement.database.dto.PurchaseDTO;
import com.syncstoremanagement.database.dto.ReportDTO;
import com.syncstoremanagement.database.model.Product;
import com.syncstoremanagement.database.model.Purchase;
import com.syncstoremanagement.database.model.Report;
import com.syncstoremanagement.database.model.SyncLogReport;
import com.syncstoremanagement.database.repository.ReportRepository;
import com.syncstoremanagement.database.repository.SyncLogReportRepository;
import com.syncstoremanagement.service.ProductService;
import com.syncstoremanagement.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ProductService productService;
    private final SyncLogReportRepository syncLogReportRepository;

    public ReportServiceImpl(ReportRepository reportRepository,
                             ProductService productService,
                             SyncLogReportRepository syncLogReportRepository) {
        this.reportRepository = reportRepository;
        this.productService = productService;
        this.syncLogReportRepository = syncLogReportRepository;
    }

    @Override
    public List<Long> sync(List<ReportDTO> listDTO) {
        if (listDTO == null || listDTO.isEmpty()) {
            return List.of();
        }

        List<Long> appIds = listDTO.stream()
                .map(ReportDTO::getId)
                .toList();

        List<Report> reports = this.reportRepository.findAllByAppIdIn(appIds);

        Map<Long, Report> reportMap = reports.stream()
                .collect(Collectors.toMap(Report::getAppId, Function.identity()));

        List<Report> newReports = new ArrayList<>();

        for (ReportDTO dto : listDTO) {
            Report report = reportMap.get(dto.getId());

            if (report != null) {
                setFields(dto, report);
            } else {
                report = new Report();
                setFields(dto, report);
                newReports.add(report);
            }
        }

        this.syncLogReportRepository.save(new SyncLogReport(newReports.size(), reports.size()));

        reports.addAll(newReports);
        this.reportRepository.saveAll(reports);

        return appIds;
    }

    private void setFields(ReportDTO dto, Report purchase) {
        Product product = this.productService.findProductByName(dto.getProduct().getName());

        if (product == null) {
            product = this.productService.save(dto.getProduct());
        }

        purchase.setProduct(product);
        purchase.setQuantity(dto.getQuantity());
        purchase.setCreatedAt(dto.getCreatedAt());
        purchase.setAppId(dto.getId());
    }
}
