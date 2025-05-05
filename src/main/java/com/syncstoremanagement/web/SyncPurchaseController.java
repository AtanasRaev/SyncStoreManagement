package com.syncstoremanagement.web;

import com.syncstoremanagement.database.dto.PurchaseDTO;
import com.syncstoremanagement.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncPurchaseController {
    private final PurchaseService purchaseService;

    public SyncPurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchases")
    public ResponseEntity<Map<String, List<Long>>> syncPurchase(@RequestBody List<PurchaseDTO> listDTO) {
        List<Long> syncAppIds = this.purchaseService.sync(listDTO);
        return ResponseEntity.ok(Map.of(
                "appIds", syncAppIds
        ));
    }
}
