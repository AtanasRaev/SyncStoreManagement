package com.syncstoremanagement.service.impl;

import com.syncstoremanagement.database.dto.PurchaseDTO;
import com.syncstoremanagement.database.model.Product;
import com.syncstoremanagement.database.model.Purchase;
import com.syncstoremanagement.database.model.SyncLogPurchase;
import com.syncstoremanagement.database.repository.PurchaseRepository;
import com.syncstoremanagement.database.repository.SyncLogPurchaseRepository;
import com.syncstoremanagement.service.ProductService;
import com.syncstoremanagement.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final SyncLogPurchaseRepository syncLogPurchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               ProductService productService,
                               SyncLogPurchaseRepository syncLogPurchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.syncLogPurchaseRepository = syncLogPurchaseRepository;
    }

    @Transactional
    @Override
    public List<Long> sync(List<PurchaseDTO> listDTO) {
        if (listDTO == null || listDTO.isEmpty()) {
            return List.of();
        }

        List<Long> appIds = listDTO.stream()
                .map(PurchaseDTO::getId)
                .toList();

        List<Purchase> purchases = this.purchaseRepository.findAllByAppIdIn(appIds);

        Map<Long, Purchase> purchaseMap = purchases.stream()
                .collect(Collectors.toMap(Purchase::getAppId, Function.identity()));

        List<Purchase> newPurchases = new ArrayList<>();

        for (PurchaseDTO dto : listDTO) {
            Purchase purchase = purchaseMap.get(dto.getId());

            if (purchase != null) {
                setFields(dto, purchase);
            } else {
                purchase = new Purchase();
                setFields(dto, purchase);
                newPurchases.add(purchase);
            }
        }

        this.syncLogPurchaseRepository.save(new SyncLogPurchase(newPurchases.size(), purchaseMap.size()));

        purchases.addAll(newPurchases);
        this.purchaseRepository.saveAll(purchases);

        return appIds;
    }

    private void setFields(PurchaseDTO dto, Purchase purchase) {
        Product product = this.productService.findProductByName(dto.getProduct().getName());

        if (product == null) {
            product = this.productService.save(dto.getProduct());
        }

        purchase.setProduct(product);
        purchase.setQuantity(dto.getQuantity());
        purchase.setPrice(dto.getPrice());
        purchase.setCreatedAt(dto.getCreatedAt());
        purchase.setAppId(dto.getId());
    }
}
